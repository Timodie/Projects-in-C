/**
 * Defines a bounded sized set of non-negative ints.
 * The implementation uses a list that is populated
 * with ListNode objects.  All ListNode objects are
 * allocated when a set is created, and simply move
 * around in the list thereafter.
 * 
 * Each method is implemented so that it makes at
 * most one traversal of the linked list, i.e., is
 * as efficient as it can be in terms of using
 * getNext and setNext on the ListNodes in the list.
 *
 * @author Eliot Moss
 *
 * Copyright 2015 Eliot Moss
 */

public class ListBoundedIntSet implements BoundedIntSet {

  /**
   * points to the first node of the list
   */
	
  private ListNode head;
  
  /**
   * Builds a bounded int set of a particular size.
   * This implementation pre-allocates all the nodes, and reuses
   * the same nodes for this set from here on.
   * @param size an int giving the desired size, assumed > 0
   */
  public ListBoundedIntSet (final int size) {
    ListNode current = null;
    int i = size;
    while (--i >= 0) {
      final ListNode node = new ListNode();
      node.setNext(current);
      current = node;
    }
    head = current;
  }

  /**
   * Creates a bounded int set with the given elements in the
   * given order -- handy for testing.  Note that non-negative
   * elements must come first, followed by any -1 elements (to
   * create a bounded int set of a large size than the initial
   * non-empty elements).
   * 
   * @param elements an int[] giving the initial elements of the set
   */
  public ListBoundedIntSet (final int [] elements) {
    ListNode current = null;
    int i = elements.length;
    while (--i >= 0) {
      final ListNode node = new ListNode();
      node.setNext(current);
      node.value = elements[i];
      current = node;
    }
    head = current;
  }

  /**
   * @return an int giving the number of values present in the set
   */
  public int size () {
    int count = 0;
    ListNode curr = head;
    while (true) {
      if (curr == null || curr.value < 0) {
        return count;
      } else {
        ++count;
        curr = curr.getNext();
      }
    }
  }

  /** 
  * @return an int giving the maximum number of values the set can contain
   * (We assume this operation is rare, so we do not store the size.)
   */
  public int capacity () {
    int count = 0;
    ListNode curr = head;
    while (true){
      if (curr == null) {
        return count;
      } else {
        ++count;
        curr = curr.getNext();
      }
    }
  }


  /**
   * Pulls node curr to the head of the list, given a pointer to the
   * previous node.  For convenience, the method does nothing if curr is null.
   * @param prev the previous ListNode; null if curr is at the head of the list
   * @param curr the ListNode to pull to the head of the list;
   */
  private void pullToHead (final ListNode prev, final ListNode curr) {
    // TODO: Have this actually pull node curr to the head of the list,
    // first checking that neither prev not curr are null
	  if(prev!=null && curr!=null){
	  	//if(curr.getNext().value!=-1)
		prev.setNext(curr.getNext());
		curr.setNext(head);
		head = curr;
	  }
  } 
  /**
   * Determines whether a given (presumed non-negative) value is in the set;
   * if it is present, it pulls the node to the head of the list, on the notion
   * that this may improve average performance in the typical case.
   * @param value an int giving the value whose presence should be tested
   * @return a boolean, true iff the value is in the set
   */
  public boolean contains (final int value) {
    // Note: It would be nice if we could use a loop of this form:
    // for (final ListNode curr = head; curr != null; curr = curr.getNext()) { ... }
    // The problem is, we need to know the node *before* the one we are searching for
    // so that we can do the unchaining necessary to pull the node to the head of the
    // list.  Notice the pattern using prev and curr.

    // TODO: when the node is found, pull it to the head of the list
    ListNode prev = null;
    ListNode curr = head;
   // prev=curr;
    while (true) {
      if (curr == null || curr.value == -1) {
        return false;
      } else if (curr.value == value){ //&& curr.getNext()!=null) {
    	//prev=curr;
    	 // pre//v.setNext(curr.getNext());
    	  //ListNode temp =curr;
    	  //curr.setNext(null);
    	  //temp.setNext(head);
    	 // head=curr;
    	  pullToHead(prev,curr);
        return true;
      } else {
        prev = curr;
        curr = curr.getNext();
      }
    }
  }

  /**
   * Removes a (presumed non-negative) value if it is in the set;
   * this implementation works by shuffling values one element toward
   * the head of the list.
   * @param value an int giving the value to be removed
   * @return a boolean indicating whether the operation changed the set, i.e.,
   * true if the item was present, false if it was not
   */
  public boolean remove (final int value) {
    // Note: there is an invariant (rule) that the empty nodes must be at the
    // end of the list
    ListNode curr = head;
    while (true) {
      if (curr == null || curr.value == -1) {
        return false;
      } else if (curr.value == value) {
        break;
      }
      curr = curr.getNext();
    }
    while (true) {
      ListNode next = curr.getNext();
      if (next == null) {
        curr.value = -1;
        return true;
      } else {
        curr.value = next.value;
        if (curr.value < 0) {
          return true;
        }
      }
      curr = next;
    }
  }

  /**
   * Adds a value to the set if it is not present;
   * whether the item is present or not, the node with the
   * value in it is left at the head of the list.  (The
   * idea is that this "pull to front" behavior may improve
   * performance.)
   * @param value an int (presumed non-negative) giving the value to add
   * @return an int decribing what happened:
   *   0 means the value was present already and simply pulled to the head of the list
   *   1 means the value was not present, but an empty node could be used for it
   *   2 means the value was not present, and the value previously at the end of the
   *     list was discarded
   */
  public int add (final int value) {
    // TODO: implement the method!
	  //if we are to pullToHead we need 2 nodes
	 int result =0;
	 
	  //ListNode temp =new ListNode();
	  ListNode curr= head;
	  //ListNode next = curr.getNext();
	  ListNode prev= null;
	  while(true){
		 
		  //curr=curr.getNext();
		  if(curr.value==value){
			System.out.println("inside case 0");
			  pullToHead(prev,curr);//supposed to be prev & curr-will figure that out
			return 0;
		  }
		  if(curr.value==-1){ //value not present but there's space indicated by -1
			  //figure out pullToHead mechanism
			// next.value=value;
			  System.out.println("inside case 1");
			  pullToHead(prev,curr);
			  curr.value=value;
			  return 1;
		  }
		  //if(curr.value!=value && next!=null){
			if(curr.getNext()==null) {
		      System.out.println("inside case 2");
			  curr.value = value;
		      pullToHead(prev,curr);
			  return 2;
		  }
		  else{
			  System.out.println("inside last else");
			  
		  }
			 prev =curr;
			  curr=curr.getNext();
	  }
	 // if(head.value==value){
		//  result=0;
	  //}
//	  while(current.getNext()!=null)
//		  
//	  		if(current.value==value){
//	  			result=0;
//	  		}
//	  		if(current.value!=value){
//	  			result=1;
//	  		}
//	  		if(current.value!=value && current.getNext()==null){
//	  			result=2;
//	  		}
//	  			current=current.getNext();
//		   
	  
  }
  
}
