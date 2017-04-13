import java.util.*;

//Each node is initialized with the following. A directory or file would be a node of the tree.

public class Node {
	private boolean isDirectory;	//to check if the node is a directory
	private boolean isOpen;			//assures that a file can only be read or written to if it is open
	private List<Node> children = new ArrayList<Node>();	//list of children of a node
    private Node parent = null;		//parent node
    private String data = null;		//data of a file
    private String nameOfNode = null;	//name of the node
    private int size;	//size of file (random)
    private int fid;	//fid of an open file
    private List<Character> arr = new ArrayList<Character>();	//using an array list of characters in place of pointers for seek
	
 //constructors to initialize a node
	public Node(String data){
		this.isDirectory = false;
		this.nameOfNode = data;
		this.isOpen = false;
		this.size=0;
		this.fid = 0;
	}
	public Node(String data, Node parent){
		this.isDirectory = false;
		this.nameOfNode = data;
		this.setParent(parent);
		this.isOpen = false;
		this.size =0;
		this.fid = 0;
	}
	
	public Node(){
		this.isDirectory = false;
		this.size=0;
		this.fid = 0;
	}
	
//Methods to add or remove a child
	
	    public void addChild(String data) {	//add a child
	        Node child = new Node(data);
	        child.setParent(this);
	    }

	    public void addChild(Node child) {	//add a child
	        child.parent = this;
	        this.children.add(child);	//add child node to the parent's list of children
	    }
	    
	    public void removeChild(Node child){	//remove child from parent's list of children
	    	this.children.remove(child);
	    }


	    public boolean isRoot() {	//check if it is the root
	        return (this.parent == null);
	    }

	    public boolean isLeaf() {	//files or empty directories will be the leaf
	        if(this.children.size() == 0) 
	            return true;
	        else 
	            return false;
	    }

	    public void removeParent() {	//remove parent 
	        this.parent = null;
	    }
	    
//Getters and Setters
	    
		public List<Node> getChildren() {
		     return children;
		 }

		public void setParent(Node parent) {
		     parent.addChild(this);
		     this.parent = parent;
		 }

	    public String getData() {
	        return this.data;
	    }

	    public void setData(String data) {
	        this.data = data;
	        this.charArr(data);
	    }

	    public void getParent(){
	    	String a = this.parent.getData();
	    	System.out.println(a);
	    }
	    
	    public Node getParentNode(){
	    	return this.parent;
	    }
	    
	    public void setIsDirectory(Boolean a){
	    	this.isDirectory = a;
	    }
	    
	    public boolean getIsDirectory(Boolean a){
	    	return a;
	    }
	    
	    public void setName(String a){
	    	this.nameOfNode = a;
	    }
	    
	    public String getName(){
	    	return this.nameOfNode;
	    }
	    
	    public Boolean getOpen(){
	    	return this.isOpen;
	    }
	    
	    public void setOpen(Boolean a){
	    	this.isOpen = a;
	    }
	    
	    public void setSize(int a){
	    	this.size=a;
	    }
	    
	    public int getSize(){
	    	return this.size;
	    }
	    
	    public void setFID(int a){
	    	this.fid = a;
	    }
	    
	    public int getFID(){
	    	return this.fid;
	    }
	    
	    public void charArr(String data){	//using as pointers
	    	char[] a = data.toCharArray();
	    	int length = a.length;
	    	for (int i=0; i<length; i++){
	    		arr.add(a[i]);
	    	}
	    }
	    
	    public List<Character> getCharArr(){
	    	return this.arr;
	    }
}