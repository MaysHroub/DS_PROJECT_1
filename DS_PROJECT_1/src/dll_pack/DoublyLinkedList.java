package dll_pack;

public class DoublyLinkedList<T extends Comparable<T>> {

	private DNode<T> head, currNode;
	
	public DoublyLinkedList() {
		currNode = head = new DNode<>(null);
	}
	
	public DNode<T> getHead() {
		return head.getNext();
	}

	public void insert(T data) {
		DNode<T> newNode = new DNode<>(data);
		DNode<T> curr = head.getNext();
		
		if (curr == null) { // empty list
			head.setNext(newNode);
			newNode.setPrev(head);
			return;
		}
		
		for (; curr.getNext() != null && curr.getData().compareTo(data) < 0; 
				curr = curr.getNext());
		
		if (curr.getNext() == null && curr.getData().compareTo(data) <= 0) { // insert last
			newNode.setPrev(curr);
			curr.setNext(newNode);
		} else { // insert between or insert first
			newNode.setNext(curr);
			newNode.setPrev(curr.getPrev());
			curr.getPrev().setNext(newNode);
			curr.setPrev(newNode);
		}
	}

	public void recursiveInsert(T data) {
		if (head.getNext() == null) { // empty list
			DNode<T> newNode = new DNode<>(data);
			head.setNext(newNode);
			newNode.setPrev(head);
			return;
		}
		recursiveInsert(data, head.getNext());
	}

	private void recursiveInsert(T data, DNode<T> curr) {
		if (curr.getNext() != null && curr.getData().compareTo(data) < 0)
			recursiveInsert(data, curr.getNext());
		else {
			DNode<T> newNode = new DNode<>(data);
			if (curr.getNext() == null && curr.getData().compareTo(data) <= 0) { // insert last
				newNode.setPrev(curr);
				curr.setNext(newNode);
			} else { // insert between or insert first
				newNode.setNext(curr);
				newNode.setPrev(curr.getPrev());
				curr.getPrev().setNext(newNode);
				curr.setPrev(newNode);
			}
		}
	}

	public DNode<T> delete(T data) {
		DNode<T> curr = head.getNext();
		
		for (; curr != null && curr.getData().compareTo(data) < 0;
				curr = curr.getNext());

		if (curr.getData().compareTo(data) != 0)
			return null;
		
		if (currNode == curr) 
			if (currNode.getNext() != null) moveNext();
			else movePrev();
		
		if (curr.getNext() == null)
			curr.getPrev().setNext(null);
		else {
			curr.getNext().setPrev(curr.getPrev());
			curr.getPrev().setNext(curr.getNext());
		}
		return curr;
	}

	public DNode<T> recursiveDelete(T data) {
		return recursiveDelete(data, head.getNext());
	}

	private DNode<T> recursiveDelete(T data, DNode<T> curr) {
		if (curr == null || curr.getData().compareTo(data) >= 0) {
			if (curr.getData().compareTo(data) != 0)
				return null;
			if (currNode == curr) 
				if (currNode.getNext() != null) moveNext();
				else movePrev();
			if (curr.getNext() == null)
				curr.getPrev().setNext(null);
			else {
				curr.getNext().setPrev(curr.getPrev());
				curr.getPrev().setNext(curr.getNext());
			}
			return curr;
		}
		return recursiveDelete(data, curr.getNext());
	}

	public T find(T data) {
		DNode<T> curr = head.getNext();
		for (; curr != null && curr.getData().compareTo(data) < 0; 
				curr = curr.getNext());
		if (curr == null || curr.getData().compareTo(data) != 0)
			return null;
		return curr.getData();
	}

	public DNode<T> recursiveFind(T data) {
		return recursiveFind(data, head.getNext());
	}

	private DNode<T> recursiveFind(T data, DNode<T> curr) {
		if (curr != null && curr.getData().compareTo(data) < 0)
			return recursiveFind(data, curr.getNext());
		else if (curr == null || curr.getData().compareTo(data) != 0)
			return null;
		else
			return curr;
	}

	public int length() {
		DNode<T> curr = head.getNext();
		int count = 0;
		while (curr != null) {
			count++;
			curr = curr.getNext();
		}
		return count;
	}

	public int recursiveLength() {
		return recursiveLength(head.getNext());
	}

	private int recursiveLength(DNode<T> curr) {
		if (curr == null)
			return 0;
		return 1 + recursiveLength(curr.getNext());
	}

	public void traverse() {
		DNode<T> curr = head.getNext();
		System.out.print("Head --> ");
		while (curr != null) {
			System.out.println(curr + " --> \n");
			curr = curr.getNext();
		}
		System.out.println("Null");
	}

	public void recursiveTraverse() {
		System.out.print("Head --> ");
		recursiveTraverse(head.getNext());
		System.out.println("Null");
	}

	private void recursiveTraverse(DNode<T> curr) {
		if (curr == null)
			return;
		System.out.print(curr + " --> ");
		recursiveTraverse(curr.getNext());
	}

	@Override
	public String toString() {
		DNode<T> curr = head.getNext();
		String res = "Head --> ";
		while (curr != null) {
			res += curr + " --> ";
			curr = curr.getNext();
		}
		res += "Null";
		return res;
	}
	
	public T getCurrentData() {
		return currNode.getData();
	}
	
	public void setCurrentNode(DNode<T> node) {
		currNode = node;
	}

	public T moveNext() {
		if (currNode.getNext() != null)
			currNode = currNode.getNext();
		return currNode.getData();
	}

	public T movePrev() {
		if (length() == 0)
			currNode = head;
		else if (currNode.getPrev() != head && currNode.getPrev() != null)
			currNode = currNode.getPrev();
		return currNode.getData();
	}

}
