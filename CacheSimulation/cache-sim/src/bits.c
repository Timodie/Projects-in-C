#include "cache.h"
#include "bits.h"
#include <stdio.h>

int get_set (Cache *cache, address_type address) {
  /* TODO:
     Extract the set bits from a 32-bit address.
   */
   int tag = 32 - (cache->set_bits+cache->block_bits);

  return address<<tag >> (cache->block_bits+ tag);
}

int get_line (Cache *cache, address_type address) {
  /* TODO:
     Extract the tag bits from a 32-bit address.
   */


  return address>>((cache->set_bits+cache->block_bits));
}

int get_byte (Cache *cache, address_type address) {
  /* TODO
     Extract the block (byte index) bits from a 32-bit address.
   */
   int tag = 32 - (cache->set_bits+cache->block_bits);
    int tagset = tag+cache->set_bits;
  return address<<tagset>>tagset;
}
