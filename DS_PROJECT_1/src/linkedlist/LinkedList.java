package linkedlist;

import java.util.ArrayList;

public class LinkedList< T extends Comparable<T> > {
	
	private Node<T> head, currNode;
	
	public LinkedList() {
		currNode = head = new Node<>(null);
	}
	
	public Node<T> getHead() {
		return head.getNext();
	}
	
	public void insert(T data) {
		Node<T> newNode = new Node<>(data);
		
		Node<T> prev = head, curr = head.getNext();
		for (; curr != null && curr.getData().compareTo(data) < 0; 
				prev = curr, curr = curr.getNext());
		
		if (prev == head) {  // case 1: insert first
			prev.setNext(newNode);
			newNode.setNext(curr);
			currNode = newNode;
		}
		else if (curr == null)  // case 2: insert at the end
			prev.setNext(newNode);
		
		else { // case 3: insert between two nodes
			newNode.setNext(curr);
			prev.setNext(newNode);
		}
	}
	
	public T find(T data) {
		Node<T> curr = head.getNext();
		for (; curr != null && curr.getData().compareTo(data) < 0;
				curr = curr.getNext());
		if (curr != null && curr.getData().compareTo(data) == 0) 
			return curr.getData();
		return null;
	}
	
	public T recursiveFind(T data) {
		return recursiveFind(head.getNext(), data);
	}

	private T recursiveFind(Node<T> start, T data) {
		if (start == null) return null;
		if (start.getData().compareTo(data) == 0)
			return start.getData();
		else 
			return recursiveFind(start.getNext(), data);
	}
	
	public Node<T> recursiveDelete(T data) {
		return recursiveDelete(null, head.getNext(), data);
	}
	
	private Node<T> recursiveDelete(Node<T> prev, Node<T> curr, T data) {
		if (curr == null) return null;
		if (curr.getData().compareTo(data) == 0) {
			if (currNode == curr) moveNext();
			if (prev == null) // delete the first element
				head.setNext(curr.getNext());
			else if (curr.getNext() == null) // delete the last element
				prev.setNext(null);
			else // delete between two elements
				prev.setNext(curr.getNext());
			return curr;
		}
		return recursiveDelete(curr, curr.getNext(), data);
	}
	
	public Node<T> delete(T data) {
		Node<T> prev = null, curr = head.getNext();
		for (; curr != null && curr.getData().compareTo(data) < 0; 
				prev = curr, curr = curr.getNext());
		if (curr != null && curr.getData().compareTo(data) == 0) {
			
			if (currNode == curr) moveNext();
			
			if (prev == null) // delete the first element
				head.setNext(curr.getNext());
			else if (curr.getNext() == null) // delete the last element
				prev.setNext(null);
			else // delete between two elements
				prev.setNext(curr.getNext());
			return curr;
		}
		return null;
	}
	
	public Node<T> deleteByEquals(T data) {
		Node<T> prev = null, curr = head.getNext();
		for (; curr != null && !curr.getData().equals(data); 
				prev = curr, curr = curr.getNext());
		if (curr != null) {
			if (currNode == curr) moveNext(); // just move the pointer in case it points to the deleted node
			if (prev == null) // delete the first element
				head.setNext(curr.getNext());
			else if (curr.getNext() == null) // delete the last element
				prev.setNext(null);
			else // delete between two elements
				prev.setNext(curr.getNext());
			return curr;
		}
		return null;
	}
	
	public void reverse() {
		Node<T> curr = head.getNext();
		while (curr != null && curr.getNext() != null) {
			Node<T> temp = curr.getNext();
			curr.setNext(curr.getNext().getNext());
			temp.setNext(head.getNext());
			head.setNext(temp);
		}
	}
	
	public void recursiveReverse() {
		recursiveReverse(head.getNext());
	}
	
	private void recursiveReverse(Node<T> curr) {
		if (curr == null || curr.getNext() == null) return;
		Node<T> temp = curr.getNext();
		curr.setNext(curr.getNext().getNext());
		temp.setNext(head.getNext());
		head.setNext(temp);
		recursiveReverse(curr);
	}

	public void traverse() {
		Node<T> curr = head.getNext();
		System.out.println("Head --> ");
		while (curr != null) {
			System.out.println(curr + " --> ");
			curr = curr.getNext();
		}
		System.out.println("Null");
	}
	
	public int length() {
		Node<T> curr = head.getNext();
		int count = 0;
		while (curr != null) {
			count++;
			curr = curr.getNext();
		}
		return count;	
	}
	
	@Override
	public String toString() {
		Node<T> curr = head.getNext();
		String linkedlist = "Head --> \n";
		while (curr != null) {
			linkedlist += curr + " --> \n";
			curr = curr.getNext();
		}
		return linkedlist + "Null";
	}
	
	public T getCurrentData() {
		return currNode.getData();
	}

	public T moveNext() {
		if (currNode.getNext() != null)
			currNode = currNode.getNext();
		else currNode = (head.getNext() != null) ? head.getNext() : head;
		return currNode.getData();
	}
}
