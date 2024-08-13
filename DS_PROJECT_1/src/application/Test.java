package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import data_pack.*;
import dll_pack.DoublyLinkedList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import linkedlist_pack.LinkedList;

public class Test {
	
	static DoublyLinkedList<District> districts;

	public static void main(String[] args) {
		DoublyLinkedList<Integer> dll = new DoublyLinkedList<>();
		dll.recursiveInsert(50);
		dll.recursiveInsert(60);
		dll.recursiveInsert(40);
		dll.recursiveInsert(90);
		dll.recursiveInsert(70);
		dll.recursiveInsert(65);
		dll.recursiveDelete(90);
		dll.traverse();
	}

	
}
