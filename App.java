package binarysearchtree;

public class App {

	public static void main(String[] args) {
		
		BST tree = new BST();
		
		tree.insert(10, "ten");
		tree.insert(4, "four");
		tree.insert(20, "twenty");
		tree.insert(2, "two");
		tree.insert(5, "five");
		tree.insert(18, "eighteen");
		tree.insert(22, "twenty two");
		tree.insert(8, "eight");
		tree.insert(17, "seventeen");
		tree.insert(19, "nineteen");
		tree.insert(21, "twenty one");
		tree.insert(24, "twenty four");
		tree.insert(9, "nine");
		tree.insert(33, "thirty three");
		
		tree.displayTreeRecursive();

		System.out.println("Min = " + tree.findMin().key);
		System.out.println("Max = " + tree.findMax().key);
				
		tree.remove(2);
		tree.displayTreeRecursive();
		System.out.println("Min = " + tree.findMin().key);
	}
}