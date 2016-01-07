#include <stdlib.h>
#include <stdio.h>
#include "cache.h"
#include "lru.h"

void lru_init_queue (Set *set) {
  LRUNode *s = NULL;
  LRUNode **pp = &s;  // place to chain in the next node
  for (int i = 0; i < set->line_count; i++) {
    Line *line = &set->lines[i];
    LRUNode *node = (LRUNode *)(malloc(sizeof(LRUNode)));
    node->line = line;
    node->next = NULL;
    (*pp) = node;
    pp = &((*pp)->next);
  }
  set->lru_queue = s;
}

void lru_init (Cache *cache) {
  Set *sets = cache->sets;
  for (int i = 0; i < cache->set_count; i++) {
    lru_init_queue(&sets[i]);
  }
}

void lru_destroy (Cache *cache) {
  Set *sets = cache->sets;
  for (int i = 0; i < cache->set_count; i++) {
    LRUNode *p = sets[i].lru_queue;
    LRUNode *n = p;
    while (p != NULL ) {
      p = p->next;
      free(n);
      n = p;
    }
    sets[i].lru_queue = NULL;
  }
}

void lru_fetch(Set* set, unsigned int tag, LRUResult* result) {
  /* TODO:
     Implement the LRU algorithm to determine which line in
     the cache should be accessed.*/
     LRUNode*current;
     Line*line;
     current = set->lru_queue;
    // if
     for(LRUNode **prevp = &(set->lru_queue); (*prevp)!=NULL;(prevp) = &((*prevp)->next)){
         current =*prevp;
        line = current->line;
//-----------Yayy we have a hit ----------------------//
        if(line->valid ==1 && line->tag==tag){
  //        printf("Hit");
          result->line =line;
          result->access = HIT;
          // *prevp = current->next;
          //   current->next = set->lru_queue;
          //   set->lru_queue = current;
            //return;

        }
//------------Alright we've got a cold miss:(------------//
      else if(line->valid==0){
         line->valid =1;
         line->tag = tag;
         result->line = line;
//printf("COLD");
         result->access = COLD_MISS;
         //result->line->tag =tag;
        //  *prevp = current->next;
        //    current->next = set->lru_queue;
        //    set->lru_queue = current;
           //return;
       }
//-----------Let's deal with a CONFLICT_MISS --------------//
      else if(current->next==NULL){
         //printf("CONFLICT_MISS");
            line->tag = tag;
            result->line = line;
            result->access =CONFLICT_MISS;
            // *prevp = current->next;
            //   current-> next = set->lru_queue;
            //   set->lru_queue = current;
            //  return;
      }
//------------alright let's move one--------------------//
else continue;

        *prevp = current->next;
          current->next = set->lru_queue;
          set->lru_queue = current;
        //  line->tag =tag;
          result->line = line;
          return;

     }

}
