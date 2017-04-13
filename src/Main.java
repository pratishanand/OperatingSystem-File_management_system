import java.util.*;

public class Main {
/**The following code creates an object for every directory and file created. The hierarchical directory structure is found in Tree.java .
 * Every Directory or file is initialized as a node in the tree (Node.java)
 * The entire memory allocation/deallocation/de-fragmentation is done in Memory.java
 * Each file is assigned a unique FID. That fid is stored in the memory to show it is occupied
 * Memory is assumed to be an array
 */
	public static void main(String[] args) {

		List<String> directoryNames = new ArrayList<String>();	//adding all directory names to a list to ensure every directory is a valid name
		Tree directory = new Tree();	//initializing tree
		System.out.println("Enter the name of the opening directory:");	
		Scanner sc = new Scanner(System.in);	//name of any directory that you would open -- root of the tree, i.e., root
		String rootName = sc.nextLine();
		directory.makeDirectory(rootName, null);	//create the root
		directoryNames.add(rootName);	//add name of root to directory
		int y = 1;
		System.out.println("Option:\n1.Create Directory\n2.Create File\n3.Search content of directory\n4.Open File\n" +
				"5.Close File\n6.Read File\n7.Write to File\n8.Append to file\n9.Delete File/Directory\n10.Print Memory Currently\n" +
				"11.Rename File\n12.Rename Directory\n13.Move File/Directory\n14.Print path\n15.Seek to a position.\n16.Seek and read from that position" +
				"\n17.Seek and Write from that position\n18.Exit");
//options in list
		
		while(y==1){
		int x = sc.nextInt();
		sc.nextLine();
		switch (x){
		case 1: 
			System.out.println("Enter name of directory:");
			String name = sc.nextLine();
			System.out.println("Enter name of parent directory:");
			String parentName = sc.nextLine();
			int flag = 0;
			for (int i=0; i<directoryNames.size();i++){
				if (directoryNames.get(i).equals(parentName)){	//assures that the name of the parent directory is valid. If invalid flag=0
					directory.makeDirectory(name,parentName);
					System.out.println("Directory Created");
					flag = 1;
					directoryNames.add(name);
					break;
				}
			}
			if (flag==0){	//Parent directory not found so no creation of directory
				System.out.println("Directory could not be created. No such parent directory.");
			}
			break;
			
		case 2:
			System.out.println("Enter name of file");
			name = sc.nextLine();
			flag = 0;
			System.out.println("Enter name of parent directory");
			parentName = sc.nextLine();
			for (int i=0; i<directoryNames.size();i++){
				if (directoryNames.get(i).equals(parentName)){		//check for parent name again like that of directory.
					directory.makeFile(name,parentName);
					flag = 1;
					System.out.println("File Created");
					break;
				}
			}
			if (flag==0){
				System.out.println("File could not be created. No such parent directory.");
			}
			break;
		
		case 3:
			System.out.println("Name of directory to search content for: ");	//lists the contents of a directory
			String search = sc.nextLine();
			directory.path(search);
			break;
			
		case 4:
			System.out.println("Name of file to open:");	//open a file
			String sr = sc.nextLine();
			directory.openFile(sr);
			break;
			
		case 5:
			System.out.println("Name of file to close:");	//close a file
			String cl = sc.nextLine();
			directory.closeFile(cl);
			break;
			
		case 6:
			System.out.println("Name of file to read data from:");	//just read from file
			String rd = sc.nextLine();
			directory.readFile(rd);
			break;
			
		case 7:
			System.out.println("Name of file to write to:");	//overwrites content of file
			String nm = sc.nextLine();
			System.out.println("Enter data to be written:");
			String dat = sc.nextLine();
			directory.writeFile(nm, dat);
			break;
			
		case 8:
			System.out.println("Name of file to append to:");	//adding data to end of file
			String nma = sc.nextLine();
			System.out.println("Enter data to be added:");
			String data = sc.nextLine();
			directory.appendToFile(nma, data);
			break;
			
		case 9:
			System.out.println("Enter name of file or directory to delete:");	//deleting a directory would delete its child as well. Not in case of a file.
			String del = sc.nextLine();
			directory.delete(del);
			break;
			
		case 10:
			directory.printMem();	//print current memory allocation for files
			break;
			
		case 11:
			System.out.println("Enter name of original file");	//change name of file
			String originalName = sc.nextLine();
			System.out.println("Enter new name of file");
			String newName = sc.nextLine();
			directory.renameFile(originalName, newName);
			break;
			
		case 12:
			System.out.println("Enter name of original directory");	//rename a directory
			String original = sc.nextLine();
			System.out.println("Enter new name of directory");
			String newn = sc.nextLine();
			directory.renameDirectory(original, newn);
			break;

		case 13:
			System.out.println("Enter name of file/directory that is supposed to be moved");	//moving a directory and its contents or just a file
			String file = sc.nextLine();
			System.out.println("Enter destination name of directory");
			String dest = sc.nextLine();
			directory.move(file,dest);
			break;
			
		case 14:
			System.out.println("Enter name of file/directory whose path is to be found");	//print path to a directory
			String path = sc.nextLine();
			directory.pathTo(path);
			break;
			
		case 15:
			System.out.println("Enter name of file");	//character at a certain position in a file
			String f = sc.nextLine();
			System.out.println("Enter position number to be found");
			int gh = sc.nextInt();
			sc.nextLine();
			directory.seek(f,gh);
			break;
			
		case 16:
			System.out.println("Enter name of file");	//to read from a certain position to a certain position in a file
			String fa = sc.nextLine();
			System.out.println("Enter position number to begin from");
			int gha = sc.nextInt();
			System.out.println("Enter position number to end at");
			int ghb = sc.nextInt();
			sc.nextLine();
			directory.seekAndRead(fa,gha,ghb);
			break;
			
		case 17:
			System.out.println("Enter name of file");	//to overwrite from a certain position
			String faa = sc.nextLine();
			System.out.println("Enter the text that is supposed to be added");
			String ghba = sc.nextLine();
			System.out.println("Enter position number to overwrite from");
			int ghaa = sc.nextInt();
			sc.nextLine();
			directory.seekAndWrite(faa,ghaa,ghba);
			break;
			
		case 18:		//exit and break;
			y=0;
			return;
		}
	}	
}
}