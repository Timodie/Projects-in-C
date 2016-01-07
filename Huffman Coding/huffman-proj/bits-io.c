/********************************************************************

 The bits-io module is used to encode bits into an output file, and decode
 them from an input file. The bits-io module buffers output/input bits into an
 internal byte-sized buffer using a special format to know when it is supposed
 to write or read a full byte to the output or input file. Here are the
 details for both writing and reading bits to a file:

 WRITING BITS

   Because we are using the stdio function fputc to write a byte to an output
   file we are required by its interface to deal with bytes, not bits, when
   writing.  That is, it is not possible to write a single bit to an output
   file in Unix - so, we must buffer the bits that we ultimately want to write
   to the output file into a byte. When the byte is full we can then write it
   to the output file.

   To do this correctly, we invented a byte format such that we know when a
   byte is "full". (It is also possible to use a counter, but we desired a
   self-describing format that works nicely in the case of a partially-used
   last byte, too.)  When a byte is full we can then write it to the output
   file and start with an empty byte. The byte format we chose for this
   assignment begins with the following format:

     B = 11111110

   This bit representation can easily be specified in C using the bitwise
   complement operator on the value 1: ~1.  Since we are using gcc, we used a
   bit literal, however: 0b11111110.  (This is not available in all C
   compilers, so it is good that we used a #define for it :-) ).

   When we write a bit (say 1) to this byte B we write the bit into the least
   significant bit with other bits shifted left by 1. This will result in:

     B = 11111101
                ^
                |---------- bit 1 we just "wrote"

   If we were to then write the bit 0 to B it would result in the following
   byte:

     B = 11111010
                ^
                |---------- bit 0 we just "wrote"

   Using this format we use the left-most 0 bit as a delimiter between bits
   not written and bits written:

     B = [bits not written]0[bits written], where [bits not written], if any,
    are all 1 bits

   In this example, we have the following:

     B = 11111 0 10
          NW   D W

   Where NW are the not written bits, D is the delimiter 0, and W are the
   written bits. We would keep writing bits until the byte B is full. A full
   byte would look like this (where the bits a-g represents the bits written):

     B = 0abcdefg

   Thus, if the most significant bit is 0 we know the byte is full and we are
   now prepared to write the byte to the output file using fputc.  After we
   write the byte to the output file we then reset the byte to its
   original "empty buffer" value of 11111110.

   There is one special case, however.  It occurs when we do not have any more
   bits to write but we have not filled our byte buffer.  In this case, we
   simply write the remaining byte to the output file, and let the read
   routine deal with it.  This does not impact writing bytes, but it will
   be important to remember this case when we read bits back in from a
   compressed input file.

 READING BITS

   Because we are using the stdio function fgetc to read bytes from an input
   file, we are required by its interface to deal with bytes and not bits when
   reading (as for writing). In the common case (a byte that was full) we read
   in a byte from the compressed input file with the following format:

   B = 0abcdefg

   Our scheme for reading will be to read bits from the high end of the byte
   and to shift marker bits into the low end.  We will do this in such a way
   that we will know when we have read all the available bits.  In particular,
   the first marker bit we shift in will be a 1 and all subsequent marker bits
   will be 0.

   Again considering the common case, we prepare the newly read byte by
   shifting it left and inserting a 1 into the low bit, giving:

   B = abcdefg1

   We can then read a bit from the high end of the byte (the one marked 'a'
   here) and shift in a 0.  This will give;

   B = bcdefg10

   The next read will obtain 'b' and shift in another 0:

   B = cdefg100

   Subsequent reads cause B to evolve as shown here:

   B = defg1000
   B = efg10000
   B = fg100000
   B = g1000000
   B = 10000000 <-- indicates no more bits -- try next byte

   When we reach the state where B is 10000000, which cannot happen otherwise,
   we know we have read all bits and the next bit read should fetch a new
   byte.

   The partial word case works similarly in terms of what we shift in, namely
   a 1 for the first shift and a 0 for any later shifts.  But before we get to
   the first bit to read, we will have to shift multiple times, namely until
   the high order bit is a 0 (as for the full-byte case), and then once more
   (also as for the full byte case).  Here is an example of a partial byte
   holding only 4 bits that were written.  The byte B as written to the file
   and as read by fgetc is:

   B = 1110abcd

   Before reading bits out, we shift, following the rule of first bit in is 1,
   and the rest are 0, until the high bit is 0:

   B = 110abcd1
   B = 10abcd10
   B = 0abcd100

   Now we shift once more:

   B = abcd1000

   and can read bits out as before, with the same stopping condition:

   B = bcd10000
   B = cd100000
   B = d1000000
   B = 10000000 <-- stopping condition

   Finally, if B has value 10000000 and fgetc returns EOF, we stop.

   A note about end-of-file: fgetc returns EOF on end of file, which is -1.
   This is different from the normal char values of 0 through 255, when
   considered as an int.  (This is why fgetc returns an int, not a char!)
   Normally you could get yourself into trouble reading directly into an
   unsigned char and looking for EOF.  For one thing, if you do 'unsigned char
   c = fgetc(...);' and then try 'c == EOF', the == will always be false.
   This is because 'c' will be in the range 0 through 255 while EOF is -1
   ... even if you read an EOF into 'c'!  The EOF value, when truncated and
   stored into 'c' will be 255 (0b11111111).

   In this case, however, we have designed our bit packing format such that a
   legitimate byte will always contain a 0.  Therefore, you can safely compare
   the value of 'c' against 0b1111111 to test for EOF.  We named that value
   EOF_VALUE.  Note that, as previously mentioned, you should not comapre
   against EOF!  Compare against EOF_VALUE!

 *******************************************************************/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "bits-io.h"
#include "tree.h"

/**
 * This structure is used to maintain the writing/reading of a
 * compressed file.
 */
struct BitsIOFile {
  FILE *fp;            // The output/input file
  int count;           // Number of bytes read/written
  char mode;           // The mode 'w' for write and 'r' for read
  unsigned char byte;  // The byte buffer to hold the bits we are
		       // reading/writing
};

#define NO_BITS_WRITTEN ((unsigned char)(0b11111110))
#define ALL_BITS_READ   ((unsigned char)(0b10000000))
#define EOF_VALUE       ((unsigned char)(EOF))  // will be 0b11111111


/**
 * Opens a new BitsIOFile. Returns NULL if there is a failure.
 *
 * The `name` is the name of the file.
 * The `mode` is "w" for write and "r" for read.
 */
BitsIOFile *bits_io_open (const char *name, const char *mode) {

  FILE *fp = fopen(name, mode);

  if (fp == NULL) {
    return NULL;
  }

  char mode_letter = mode[0];

  BitsIOFile *bfile = (BitsIOFile*)(malloc(sizeof(BitsIOFile)));
  bfile->fp    = fp;
  bfile->count = 0;
  bfile->mode  = mode_letter;
  bfile->byte  = ((mode_letter == 'w') ? NO_BITS_WRITTEN : ALL_BITS_READ);
  return bfile;
}


/**
 * return the number of bytes read/written so far
 */
int bits_io_num_bytes (BitsIOFile *bfile) {

  assert(bfile != NULL);

  return bfile->count;
}


/**
 * Close the BitsIOFile. Returns EOF if there was an error.
 */
int bits_io_close (BitsIOFile *bfile) {

  assert(bfile != NULL);

  // Write the current (last) byte if we are in 'w' mode:
  if (bfile->mode == 'w' && bfile->byte != NO_BITS_WRITTEN) {
    bfile->byte = fputc(bfile->byte, bfile->fp);
    ++bfile->count;

    // Check to see if there was a problem:
    if (bfile->byte == EOF_VALUE) {
      // We will follow the fputc return value convention
      // which is to return EOF:
      return EOF;
    }
  }

  fflush(bfile->fp);
  fclose(bfile->fp);
  free(bfile);

  return 0;
}


/**
 * Read a bit from the BitsIOFile.
 * Returns 0 or 1 for a bit read,
 * or EOF (-1) if there are no more bits to read
 */
int bits_io_read_bit (BitsIOFile *bfile) {
  assert(bfile != NULL);

  unsigned char mask = (unsigned char)(1<<7);

  if((bfile->byte) == EOF_VALUE)
  return EOF;

  if((bfile->byte) == ALL_BITS_READ){
    unsigned char c = fgetc(bfile->fp);

    unsigned char result;
    int shift = 1;

    if(c == EOF_VALUE)
    return EOF;

    else{
      (bfile->count)++;
      if((c & mask) == 0){
        result = (c<<1)+1;
      }
      else{
        while((c & mask) != 0){
          c = (c << 1) + shift;
          shift = 0;
        }
        result = c<<1;
      }
    }
    bfile->byte = result;
  }

  unsigned char r = bfile->byte;

  bfile->byte = r<<1;

  return (r & mask) >> 7;
}

/**
 * Writes the given bit (1 or 0) to the BitsIOFile.
 */
int bits_io_write_bit (BitsIOFile *bfile, int bit) {
  assert(bfile != NULL);
  assert((bit & 1) == bit);

  // Write the bit into the byte:
  bfile->byte = (bfile->byte << 1) | bit;

  // Check if the byte is full and write if it is.
  // A byte is full if its left-most bit is 0:
  if ((bfile->byte >> 7) == 0) {
    bfile->byte = fputc(bfile->byte, bfile->fp);
    ++bfile->count;

    // Check to see if there was a problem:
    if (bfile->byte == EOF_VALUE) {
      // We will follow the fputc return value convention
      // which is to return EOF:
      return EOF;
    }

    // Reset the byte:
    bfile->byte = NO_BITS_WRITTEN;
  }

  return bit;
}


/**
 * Writes the Huffman tree to the BitsIOFile.
 *
 * We need to write the tree to the file so that we can use it when we
 * decode the compressed file.
 */
int bits_io_write_tree (BitsIOFile *bfile, TreeNode *tree) {
  // If the mode is not for writing we return -1.
  if (bfile->mode != 'w') {
    return -1;
  }

  tree_serialize(tree, bfile->fp);
  return tree_size(tree);
}


/**
 * Reads the huffman tree from the BitsIOFile.
 *
 * We need to do this first so we have a tree that will be used to
 * decode the rest of the input.
 */
TreeNode *bits_io_read_tree (BitsIOFile *bfile) {
  // If the mode is not for writing we return -1.
  if (bfile->mode != 'r') {
    return NULL;
  }

  return tree_deserialize(bfile->fp);
}
