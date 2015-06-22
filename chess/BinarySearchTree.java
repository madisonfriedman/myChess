package chess;

public class BinarySearchTree {
	private Node root;
	
	 /**
	 Constructs an empty tree.
	 */
	 public BinarySearchTree()
	 {
		 root = null;
	 }
	
	 /**
	 Inserts a new node into the tree.
	 @param obj the object to insert
	 */
	 public void add(Comparable obj)
	 {
		 Node newNode = new Node();
		 newNode.data = obj;
		 newNode.left = null;
		 newNode.right = null;
		 if (root == null) root = newNode;
		 else root.addNode(newNode);
	 }
	 
	 public void print()
	  {
		 if (root != null)
			 root.printNodes();
		 System.out.println();
	  }
	 
	  /**
	  A node of a tree stores a data item and references
	  to the child nodes to the left and to the right.
	  */
	  class Node
	  {
		  public Comparable data;
		  public Node left;
		  public Node right;
	 
	  /**
	  Inserts a new node as a descendant of this node.
	  @param newNode the node to insert
	  */
	  public void addNode(Node newNode)
	  {
		  int comp = newNode.data.compareTo(data);
		  if (comp < 0)
		  {
			  if (left == null) left = newNode;
			  else left.addNode(newNode);
		  }
		  if (comp > 0)
		  {
			  if (right == null) right = newNode;
			  else right.addNode(newNode);
		  }
	  }
	 
	  /**
	  Prints this node and all of its descendants
	  in sorted order.
	  */
	  public void printNodes()
	  {
		  if (left != null)
			  left.printNodes();
		  System.out.print(data + " ");
		  if (right != null)
			  right.printNodes();
	  }
	  }
	 
	 
	 
	 
}
