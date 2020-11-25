package binarysearchtree;

import java.util.Stack;

public class BST {

	private Node root;
	
	public void insert(int key, String value) {
		
		Node newNode = new Node(key, value);
		
		if (root == null) {
			root = newNode;
		}
		else {
			Node current = root;
			Node parent;
			
			while (true) {
				
				parent = current;
				
				if (key < current.key) {
					
					current = current.leftChild;
					if (current == null) {	// It's parent is the leaf node
						parent.leftChild = newNode;
						return;
					}
				}
				else {
					current = current.rightChild;
					if (current == null) {
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}
	
	public Node findMin() {
		
		Node current = root;
		Node last = null;
		
		while (current != null) {
			
			last = current;
			current = current.leftChild;
		}
		
		return last;
	}
	
	public Node findMax() {
		
		Node current = root;
		Node last = null;
		
		while (current != null) {
			
			last = current;
			current = current.rightChild;
		}
		
		return last;
	}
	
	public boolean remove(int key) {
		
		Node currentNode = root;
		Node parentNode = root;
		
		boolean isLeftChild = false;
		
		// searching to find the node with the key to delete
		while (currentNode.key != key) {
			
			parentNode = currentNode;
			
			if (key < currentNode.key) {
				isLeftChild = true;
				currentNode = currentNode.leftChild;
			} else {
				currentNode = currentNode.rightChild;
				isLeftChild = false;
			}
			
			if (currentNode == null) {
				return false;
			}
		}
		
		// found the node
		Node nodeToDelete = currentNode;
		
		// if node is a leaf
		if (nodeToDelete.leftChild == null && nodeToDelete.rightChild == null) {
			
			if (nodeToDelete == root) {
				root = null;
			}
			else if (isLeftChild) {
				parentNode.leftChild = null;
			} else {
				parentNode.rightChild = null;
			}
		}
		// if node has one child that is on the left
		else if (nodeToDelete.rightChild == null) {
			
			if (nodeToDelete == root) {
				root = nodeToDelete.leftChild;
			}
			else if (isLeftChild) {
				parentNode.leftChild = nodeToDelete.leftChild;
			} else {
				parentNode.rightChild = nodeToDelete.leftChild;
			}
		}
		// if node has one child that is on the right
		else if (nodeToDelete.leftChild == null) {
			
			if (nodeToDelete == root) {
				root = nodeToDelete.rightChild;
			}
			else if (isLeftChild) {
				parentNode.leftChild = nodeToDelete.rightChild;
			} else {
				parentNode.rightChild = nodeToDelete.rightChild;
			}
		}
		// if node has two children (tricky)
		else {
			Node successor = getSuccessor(nodeToDelete);
			
			// connects parent of nodeToDelete to successor instead
			if (nodeToDelete == root) {
				root = successor;
			}
			else if (isLeftChild) {
				parentNode.leftChild = successor;
			} else {
				parentNode.rightChild = successor;
			}
			
			successor.leftChild = nodeToDelete.leftChild;
		}
		
		return true;
	}
	
	private Node getSuccessor(Node nodeToDelete) {
		
		Node successorParent = nodeToDelete;
		Node successor = nodeToDelete;
		
		Node current = nodeToDelete.rightChild;	// go to the right child
		
		// start going left down the tree util node has no left child
		while (current != null) {
			
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		
		// if successor is not a right child
		if (successor != nodeToDelete.rightChild) {
			
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = nodeToDelete.rightChild;
		}
		
		return successor;
	}
	
	public void displayTreeRecursive() {
		
		System.out.println("====================");
		
		if (root == null) {
			System.out.println("Empty tree");
			return;
		}
		
		int depth = checkDepth(root, 0); // = tree lvl with null nodes only
		int maxReachLvl = 0;
		
		// root display
		Node startNode = root;
		displaySpace(pow(2, depth));
		displayKey(startNode);
		endline();
		
		// display the rest of the tree
		
		// proper amount of spaces before the first element of the next lvl
		displaySpace(pow(2, depth - (maxReachLvl + 1)));
		
		while (true) {
			++maxReachLvl;
			// - go from the 1st lvl (root's children) to maxReachLvl
			// - print max reached lvl
			displayLvl(1, maxReachLvl, startNode.leftChild, true, depth);
			displayLvl(1, maxReachLvl, startNode.rightChild, false, depth);
			endline();	// print newline character
			displaySpace(pow(2, depth - (maxReachLvl + 1)));
			
			if (maxReachLvl == depth - 1) {
				//System.out.println("Depth: " + depth);
				System.out.println("====================");
				return; // we reached null nodes in every direction
			}
		}
	}
	
	private int checkDepth(Node currNode, int currDepth) {
		
		while (currNode != null) {
			
			++currDepth;
			int leftDepth = checkDepth(currNode.leftChild, currDepth);
			int rightDepth = checkDepth(currNode.rightChild, currDepth);
			
			if (leftDepth >= rightDepth) {
				return leftDepth;
			}
			else return rightDepth;
		}
		
		// if currNode is null
		return currDepth;
	}
	
	private void displaySpace(int count) {
		for (int i = 0; i < count; ++i) {
			System.out.print(" ");
		}
	}
	
	private void displayKey(Node toDisplay) {
		// we assume, that the greatest value of a node can contain two digits
		if (toDisplay.key > 9) {
			System.out.print(toDisplay.key);
		} else {
			System.out.print(toDisplay.key + " ");
		}
	}
	
	private void endline() {
		System.out.println();
	}
	
	
	private int pow(int x, int y) {
		
		if (y == 0)
			return 1;
		
		int temp = x;
		for (int i = 1; i < y; ++i) {
			temp *= x;
		}
		return temp;
	}
	
	private boolean displayLvl(int currentLvl, int maxReachLvl, Node currNode, boolean isLeftChild, int treeDepth) {
		
		if (currNode == null) {
			
			int lvlDiff = maxReachLvl - currentLvl;
			if (lvlDiff >= 1) {
				int nullNodes = pow(2, lvlDiff);	// number of null nodes to display
				
				for (int i = 0; i < nullNodes; ++i) {
					System.out.print("--");
					displaySpace(pow(2, (treeDepth - maxReachLvl + 1)) - 2);
				}
			}
			else {
				System.out.print("--");
				displaySpace(pow(2, (treeDepth - currentLvl + 1)) - 2);
			}
			
			return false;	// there is nothing to display in this branch anymore
		}
		
		if (currentLvl == maxReachLvl) { // we reached lowest lvl and we know that a node is not null
			displayKey(currNode);	
			displaySpace(pow(2, (treeDepth - currentLvl + 1)) - 2);
			return true;
		}
		
		// otherwise
		++currentLvl; 
		boolean left = displayLvl(currentLvl, maxReachLvl, currNode.leftChild, true, treeDepth);
		boolean right = displayLvl(currentLvl, maxReachLvl, currNode.rightChild, false, treeDepth);
		
		if ((left == false) && (right == false))
			return false; // we reached null leaves in every direction

		return true;
	}
	
	private void displayChildren(Node parent) {
		
		if (parent.leftChild != null) {
			displayKey(parent.leftChild);
		} else {
			System.out.println("-");
		}
		if (parent.rightChild != null) {
			displayKey(parent.rightChild);
		} else {
			System.out.println("-");
		}
	}
}
