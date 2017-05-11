//     Author: Omri Gotlieb
//     Github: https://github.com/Omrigotlieb
//     HackerRank: https://www.hackerrank.com/omrigotlieb
//     Portfolio: http://omrigotlieb.com

package temp;
public class BinaryTree {

	static Node tree;

	public static void main(String[] args) {
		tree = new Node(5);
		Node root = tree;
		Node nodeA = new Node(4);
		insert(root, nodeA);
		insert(root, new Node(8));
		insert(root, new Node(2));
		insert(root, new Node(6));
		insert(root, new Node(9));
		insert(root, new Node(7));
		insert(root, new Node(1));
		insert(root, new Node(3));
		inorder(root);
		System.out.println();
		printBinaryTree(root, 0);
		System.out.println();
		deleteNode(root, getNode(root, 9));
		printBinaryTree(tree, 0);
	}


	public static boolean isBinaryTree(Node root) {
		if (root == null) return true;
		if (isLeaf(root)) return true;
		if (root.left != null) {
			if (root.left.data >= root.data) return false;
		}
		if (root.right != null) {
			if (root.right.data <= root.data) return false;
		}
		return isBinaryTree(root.left) && isBinaryTree(root.right);
	}

	public static void deleteTree(Node root) {
		root = null;
	}

	public static Node getMax(Node root) {
		if (isLeaf(root) || root.right == null) {
			return root;
		} else {
			return getMax(root.right);
		}
	}

	public static Node getMin(Node root) {
		if (isLeaf(root) || root.left == null) {
			return root;
		} else {
			return getMin(root.left);
		}
	}

	public static Node getPredecessor(Node root, Node node) {
		if (root == null) return null;
		if (node == getMin(root)) return null;
		if (node.left != null) {
			return getMax(node.left);
		}
		while (node.parent.right != node) {
			node = node.parent;
		}
		return node.parent;
	}

	public static Node getSuccessor(Node root, Node node) {
		if (node.data == getMax(root).data) return null;
		if (node.right != null) return getMin(node.right);
		while (node.parent.left != node && node.parent != null) {
			node = node.parent;
		}
		return node.parent;
	}

	public static boolean isLeaf(Node node) {
		return (node.left == null && node.right == null);
	}


	public static int getHeight(Node root) {
		if (root == null) return 0;
		return 1 + Math.max(getHeight(root.right), getHeight(root.left));
	}

	public static Node getNode(Node root, int num) {
		if (root == null) return null;
		if (root.data == num) return root;
		if (root.data > num) {
			return getNode(root.left, num);
		} else {
			return getNode(root.right, num);
		}
	}

	public static int countNodes(Node root) {
		if (root == null) return 0;
		return 1 + countNodes(root.left) + countNodes(root.right);
	}

	public static void inorder(Node root) {
		if (root.left != null) {
			inorder(root.left);
		}
		System.out.print(root.data + "-");
		if (root.right != null) {
			inorder(root.right);
		}
	}

	public static void deleteNode(Node root, Node node) {
		// delete root
		if (root == node) {
			if (node.right != null) {
				Node temp = getSuccessor(root, node);
				if (temp == null) {
					root = node.left;
					return;
				}
				deleteNode(root.right, temp);
				System.out.println(temp.data);
				root = temp;
				System.out.println(root.data);
				root.parent = null;
				root.left = node.left;
				root.left.parent = root;
				root.right = node.right;
				root.right.parent = root;
				tree = root;
				return;
			} else {
				if (node.left != null) {
					Node temp = getPredecessor(root, node);
					if (temp == null) {
						root = null;
						return;
					}
					System.out.println("Predecessor is: " + temp.data);

					deleteNode(root.left, temp);
					root = temp;
					root.parent = null;
					root.left = node.left;
					root.left.parent = root;
					root.right = node.right;
					root.right.parent = root;
				} else {
					root = null;
					return;
				}
			}
		}
		System.out.println("got here");
		//Node is leaf
		if (isLeaf(node)) {
			System.out.println("Node is leaf");
			if (node.parent.right == node) {
				node.parent.right = null;
			} else {
				node.parent.left = null;
			}
			return;
		}

		System.out.println("hot here 1");
		// Node has two children
		if (node.left != null && node.right != null) {
			System.out.println("hot here 2");
			// node is right child
			System.out.println("node parent is:  " + node.parent);
			if (node.parent.right == node) {
				System.out.println("hot here 2.1");
				node.parent.right = node.left;
				getPredecessor(root, node).right = node.right;
			}
			if (node.parent.left == node) {
				System.out.println("hot here 2.2");
				node.parent.left = node.left;
				getPredecessor(root, node).right = node.right;
			}
		} else {
			System.out.println("hot here 3");
			//node has only left child
			if (node.left != null) {
				// node is right child
				if (node.parent.right == node) {
					node.parent.right = node.left;
				} else {
					node.parent.left = node.left;
				}
			} else {
				System.out.println("hot here 4");
				//node has only right child
				if (node.right != null) {
					if (node.parent.right == node) {
						node.parent.right = node.right;
					} else {
						node.parent.left = node.right;
					}
				}
			}
		}
	}

	public static void insert(Node root, Node node) {
		if (root == null) {
			root = node;
			root.parent = null;
			return;
		}

		if (root.data > node.data) {
			if (root.left == null) {
				root.left = node;
				node.parent = root;
			} else {
				insert(root.left, node);
			}
		} else {
			if (root.right == null) {
				root.right = node;
				node.parent = root;
			} else {
				insert(root.right, node);
			}
		}


	}

	public static void printBinaryTree(Node root, int level) {
		if (root == null)
			return;
		printBinaryTree(root.right, level + 1);
		if (level != 0) {
			for (int i = 0; i < level - 1; i++)
				System.out.print("|\t");
			System.out.println("|-------" + root.data);
		} else
			System.out.println(root.data);
		printBinaryTree(root.left, level + 1);
	}
}


class Node {
	public int data;
	public Node left;
	public Node right;
	public Node parent;

	public Node(int data, Node left, Node right) {
		this.data = data;
		this.left = left;
		this.right = right;
		this.parent = null;
	}

	public Node(int data) {
		this.data = data;
		this.parent = null;
		this.left = null;
		this.right = null;
	}
}





