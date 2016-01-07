#ifndef __BITSTR_H
#define __BITSTR_H

#include "tree.h"

/**
 * This structure is used to maintain the writing/reading of a
 * compressed file.
 */
typedef struct BitsIOFile BitsIOFile;

/**
 * Opens a new BitsIOFile. Returns NULL if there is a failure.
 *
 * The `name` is the name of the file.
 * The `mode` is "w" for write and "r" for read.
 */
BitsIOFile *bits_io_open (const char *name, const char *mode);

/**
 * Returns the number of bytes read/written so far
 */
int bits_io_num_bytes (BitsIOFile *bfile);

/**
 * Close the BitsIOFile. Returns EOF if there was an error.
 */
int bits_io_close (BitsIOFile *bfile);

/**
 * Read a bit from the BitsIOFile.  Returns EOF if there are no more bits.
 */
int bits_io_read_bit (BitsIOFile *bfile);

/**
 * Writes the given bit (1 or 0) to the BitsIOFile.
 */
int bits_io_write_bit (BitsIOFile *bfile, int bit);

/**
 * Writes the Huffman tree to the BitsIOFile.
 *
 * We need to write the tree to the file so that we can use it when we
 * decode the compressed file.
 */
int bits_io_write_tree (BitsIOFile *bfile, TreeNode *tree);

/**
 * Reads the Huffman tree from the BitsIOFile.
 *
 * We need to do this first so we have a tree that will be used to
 * decode the rest of the input.
 */
TreeNode *bits_io_read_tree (BitsIOFile *bfile);

#endif
