import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Hierarchical general tree structure used for directories 

public class Tree {
	private Node root;
	private List<Node> directoryNames = new ArrayList<Node>(); // list of
																// directory
																// names
	private List<Node> fileNames = new ArrayList<Node>(); // list of fileNames
	private Memory memory = new Memory(); // initializing an empty memory
	private List<Integer> fid = new ArrayList<Integer>(); // list of fid's
															// allocated
															// initialized with
															// values 1-10
															// (possible fids
															// assigned to
															// files)

	public Tree() { // constructor
		// root = new Node();
		root = null;
		for (int i = 1; i < 10; i++) { // initializing fids and providing a
										// fixed set of valid fids (1-10)
			fid.add(i);
		}
	}

	/*
	 * public void addNode(String name){ //the first node added would be set as
	 * the root if (root==null){ Node newn = new Node(name); root = newn; } }
	 */

	public void makeDirectory(String name, String parentName) { // to make a new
																// directory
		if (root == null) { // check if root is null. Create new node and set it
							// as the root
			Node newn = new Node(name);
			root = newn;
			root.setIsDirectory(true); // isDirectory would be true. Used to
										// differentiate files and directories
			directoryNames.add(root); // add name of directory to the list
			return;
		} else {
			// System.out.println("Hello");
			Node newNd = new Node(name);
			newNd.setIsDirectory(true); // needs to be set as a directory is
										// being created
			directoryNames.add(newNd); // add name to the list
			Node a = this.search(parentName); // search for the parent node
			newNd.setParent(a); // set the parent of the new node as the parent
								// node using setters
		}
	}

	public void makeFile(String name, String parentName) {
		for (int i = 0; i < this.fileNames.size(); i++) {
			if (this.fileNames.get(i).getName().equals(name)
					&& this.fileNames.get(i).getParentNode().getName().equals(parentName)) {
				System.out.println("File exists with this name in this directory. Try again with a different name.");
				return;
			}
		}
		Node newNd = new Node(name); // initialize node with name
		newNd.setIsDirectory(false); // not a directory so false
		Node a = this.search(parentName); // search for parent node
		newNd.setParent(a); // set as parent node
		fileNames.add(newNd); // add filename to the list
	}

	public Node search(String parentName) { // search for a directory
		int flag = 0;
		for (int i = 0; i < directoryNames.size(); i++) {
			String r = directoryNames.get(i).getName();
			if (r.equals(parentName)) {
				flag = 1;
				return directoryNames.get(i);
			}
		}
		if (flag == 0) {
			System.out.println("Not a directory name");
		}
		return null;
	}

	public Node searchFile(String fileName) { // search for a particular file
		int flag = 0;
		for (int i = 0; i < this.fileNames.size(); i++) {
			String r = fileNames.get(i).getName();
			if (r.equals(fileName)) {
				flag = 1;
				return fileNames.get(i);
			}
		}
		if (flag == 0) {
			System.out.println("Not a file name");
		}
		return null;
	}

	public void path(String parentName) { // to find the contents of a directory
		Node a = this.searchs(parentName); // assure name is that of a directory
		if (a == null) {
			System.out.println("Directory does not exist");
			return;
		}
		List<Node> childrenNodes = a.getChildren(); // get list of children
		if (childrenNodes.isEmpty()) {
			System.out.println("nothing in directory");
			return;
		}
		for (int i = 0; i < childrenNodes.size(); i++) { // list children of the
															// directory
			System.out.println(childrenNodes.get(i).getName());
		}
	}

	public void openFile(String fileName) {
		Node a = this.searchFile(fileName); // find node of the fileName
		if (a.getOpen()) { // if file is already open, output error
			System.out.println("File is already open");
			return;
		} else {
			if (!fid.isEmpty()) { // assure that fid is available
				a.setFID(fid.get(0)); // set any fid that is currently available
				fid.remove(0); // remove fid from the list
				if (memory.allocateMemory(a)) { // allocate memory. if it
												//  returns true, it means memory
												// has successfully been
												// allocated
					a.setOpen(true); // set open as true
					System.out.println("File opened.");
					return;
				}
			} else
				System.out.println("No space. Close an open file.");
		}
	}

	public void closeFile(String fileName) {
		Node a = this.searchFile(fileName);
		if (a.getOpen() == false) { // close file only if it is open
			System.out.println("File is already closed");
			return;
		} else {
			int fidr = a.getFID(); // if the file is closed, its fid is freed.
			fid.add(fidr); // add the fid back to the list of available fids
			memory.deallocateMemory(a); // deallocate memory of that file
			a.setOpen(false); // file is not open
			System.out.println("File Closed");
		}
	}

	public void writeFile(String fileName, String b) { // overwrite content of
														// file
		Node a = this.searchFile(fileName); // search for node with that name
											// from the list of files
		if (a.getOpen()) { // assure file is open
			a.setData(b);
		} else {
			System.out.println("File is not open. Cannot write to file.");
		}
	}

	public void readFile(String fileName) { // to read data from a file
		Node a = this.searchFile(fileName); // search for the node assigned to
											// that file name
		if (a.getOpen()) { // only if the file is open can you read from the
							// file
			String ab = a.getData();
			System.out.println(ab); // print content
		} else { // if file is closed, it cannot be read
			System.out.println("File is not open. Cannot read file.");
		}
	}

	public void appendToFile(String fileName, String toAdd) { // add data to the
																// end of the
																// file
		Node a = this.searchFile(fileName);
		if (a.getOpen()) { // assure that file is open while appending
			String b = a.getData(); // current data stored in file
			String conc = b + " " + toAdd; // concatenate files
			a.setData(conc); // set data of file again
		} else { // if file is not open, cannot be read
			System.out.println("File is not open. Cannot read file.");
		}
	}

	public void delete(String name) {
		Node a = this.searchs(name); // search whether the name of the object is
										// a directory
		Node b = this.searchFiles(name); // search whether the name of the
											// object is a file
		if (a != null) { // if it is a directory, delete the directory
			this.deleteDirectory(a);
			return;
		} else if (b != null) { // for a file, call the following method.
			this.deleteFile(b);
			return;
		} else
			System.out.println("File/Directory not found.");
	}

	public void deleteDirectory(Node a) {
		List<Node> deletes = this.paths(a.getName()); // must delete all the
														// children as well
		for (int i = 0; i < deletes.size(); i++) {
			this.delete(deletes.get(i).getName()); // recursively call for the
													// children
		}
		this.directoryNames.remove(a); // remove 'a' from the list
		Node b = a.getParentNode();
		a.removeParent(); // remove parent
		b.removeChild(a); // parent node must remove the child
		a = null;
		System.out.println("Directory deleted!");
	}

	public void deleteFile(Node a) { // files would be the leaves of the tree
										// and would have no children
		this.fileNames.remove(a); // remove name from the list
		Node b = a.getParentNode();
		b.removeChild(a); // the parent node must remove the current node from
							// its list of children
		a.removeParent(); // sets parent as null
		a = null;
		System.out.println("File deleted!");
	}

	private Node searchs(String parentName) {
		for (int i = 0; i < directoryNames.size(); i++) { // search the list of
															// directoryNames
			String r = directoryNames.get(i).getName();
			if (r.equals(parentName)) {
				return directoryNames.get(i);
			} // return the directory node if found
		}
		return null; // not a directory
	}

	private Node searchFiles(String fileName) {
		for (int i = 0; i < this.fileNames.size(); i++) { // search the list of
															// fileNames
			String r = fileNames.get(i).getName();
			if (r.equals(fileName)) {
				return fileNames.get(i); // return the node of the file found
			}
		}
		return null; // not a file
	}

	private List<Node> paths(String parentName) { // returns all the children
													// nodes of a directory. If
													// directory is empty,
													// returns null
		Node a = this.search(parentName);
		List<Node> childrenNodes = a.getChildren();
		if (childrenNodes.isEmpty()) {
			System.out.println("Nothing in directory");
			return null;
		} else
			return childrenNodes;
	}

	public void printMem() { // to print current memory
		memory.print();
	}

	public void renameFile(String originalName, String newName) { // change name
																	// of file
		Node a = this.searchFiles(originalName); // find file corresponding to
													// original name
		if (a == null) {
			System.out.println("No file of that name");
			return;
		} else {
			a.setName(newName); // set name
			System.out.println("File name successfully changed");
		}
	}

	public void renameDirectory(String originalName, String newName) { // change
																		// name
																		// of
																		// directory
		Node a = this.searchs(originalName); // find node corresponding to
												// original name
		if (a == null) {
			System.out.println("No directory of that name");
			return;
		} else {
			a.setName(newName); // set name
			System.out.println("Directory name successfully changed");
		}
	}

	public void move(String original, String dest) {
		Node a = this.searchs(original); // search for directory
		Node b = this.searchFiles(original); // search for file
		Node d = this.searchs(dest); // search name of destination directory
		if (d == null) { // if destination directory does not exist error.
			System.out.println("Destination does not exist. Error");
			return;
		}
		Scanner sc = new Scanner(System.in);
		if (a != null && b != null) { // identify whether it is a file or
										// directory
			System.out.println(
					"Both a file and directory exist with the name of the file/directory you'd like to move. Enter f if it is a file or d if it is a directory");
			String c = sc.nextLine();
			if (c.equals("f")) {
				this.moveFile(b, d);
				return;
			}
			if (c.equals("d")) {
				this.moveDirectory(a, d);
				return;
			} else {
				System.out.println("Invalid input");
				return;
			}
		} else if (a != null && b == null) { // move directory
			this.moveDirectory(a, d);
			return;
		} else if (a == null && b != null) { // move file
			this.moveFile(b, d);
			return;
		} else { // error
			System.out.println("File or Directory not found!!");
			return;
		}
	}

	private void moveFile(Node original, Node dest) { // to move file
		Node parent = original.getParentNode();
		parent.removeChild(original); // remove the child from the parent node
		dest.addChild(original); // add child node to that of the new parent
		System.out.println("File moved!");
	}

	private void moveDirectory(Node original, Node dest) { // to move directory
		Node parent = original.getParentNode();
		parent.removeChild(original); // remove the child from the parent node
		dest.addChild(original); // add child node to that of the new parent
		System.out.println("Directory and contents moved!");
	}

	public void pathTo(String name) { // to find the path to a particular
										// file/directory
		Node a = this.searchs(name);
		Node b = this.searchFiles(name);
		if (a != null) {
			this.printPath(a);
		} else if (b != null) {
			this.printPath(b);
		} else {
			System.out.println("Error");
			return;
		}

	}

	private void printPath(Node a) { // recursively call parent node till the
										// root is found
		if (a == root) {
			System.out.println(root.getName());
			return;
		} else {
			System.out.println(a.getName());
			a = a.getParentNode();
			this.printPath(a);
		}
	}

	public void seek(String filename, int position) { // seek to a particular
														// position
		Node a = this.searchFiles(filename);
		if (a == null) {
			System.out.println("File name not found");
			return;
		}
		if (a.getOpen()) {
			List<Character> b = a.getCharArr();
			System.out.println("Currently at " + position + "is this character: " + b.get(position));
			return;
		}
		if (!a.getOpen()) {
			System.out.println("File not open. Cannot seek.");
			return;
		}
	}

	public void seekAndWrite(String filename, int position, String newstring) { // overwrite
																				// a
																				// certain
																				// position
																				// on
																				// the
																				// file
		Node a = this.searchFiles(filename);
		if (a == null) {
			System.out.println("File name not found");
			return;
		}
		if (a.getOpen()) {
			List<Character> b = a.getCharArr();
			char[] n = newstring.toCharArray();
			int l = n.length;
			if (position >= b.size()) {
				System.out.println("File length does not include this position");
				return;
			}
			int cnt=0;
			for (int i = position; i < b.size(); i++) {
				b.set(i, n[i-position]);
				cnt++;
			}
			for (int i = cnt; i < l; i++) {
				b.add(n[i]);
			}
			
			StringBuilder builder = new StringBuilder(b.size());
			for (Character ch : b) {
				builder.append(ch);
			}
			a.setData(builder.toString());
			System.out.println("File updated");
			return;
		}
		if (!a.getOpen()) {
			System.out.println("File not open. Cannot seek.");
			return;
		}
	}

	public void seekAndRead(String filename, int position1, int position2) { // read
																				// from
																				// a
																				// file
																				// at
																				// a
																				// particular
																				// position
		Node a = this.searchFiles(filename);
		if (a == null) {
			System.out.println("File name not found");
			return;
		}
		if (a.getOpen()) {
			List<Character> b = a.getCharArr();
			int n = b.size();
			if (position1 >= n || position2 > n) {
				System.out.println("Positions are not in range. Error");
				return;
			}
			while (position1 != position2) {
				System.out.print(b.get(position1));
				position1++;
			}
			return;
		}
		if (!a.getOpen()) {
			System.out.println("File not open. Cannot seek.");
			return;
		}
	}
}
