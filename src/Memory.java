import java.util.*;

/**I have assumed the memory to be an array of length 60. Also the memory chunks are of 20 and a file will occupy a random number between 5-20.
Along with that, memory is only allocated when a file is open. 
*/

public class Memory {
	private int[] memSize;	//memory array
	Random ran = new Random();
	static final int MAX = 20;	//max length of file
	static final int MIN = 5;	//min length of file
	static final int MEMORY_CHUNK_SIZE = 20;	//chunk size
	static final int MEMORY_SIZE = 60;	//memory size
	
	public Memory(){
		this.memSize = new int[MEMORY_SIZE];	//Initialize memory array
	}
	
	public Boolean allocateMemory(Node a){
	    int randomNum = ran.nextInt((MAX - MIN) + 1) + MIN;	//file size
	    a.setSize(randomNum);	//set the size of the file
	    int i = MEMORY_SIZE/MEMORY_CHUNK_SIZE;	
	    int numb = a.getFID();	//get fid of file to store in the array to show it is occupied by that particular file
//	    System.out.println(randomNum);
//	    System.out.println(numb);
	    
/**check initial input of the chunks and not the entire memory. If the first bit of the chunk is occupied, that particular chunk is then skipped 
	    leading to external fragmentation.*/

	    for (int j=0; j<MEMORY_SIZE; j+=MEMORY_CHUNK_SIZE){	
	    	if (memSize[j]==0){	//if initial entry is 0, fill chunk with fid value
	    		for (int k=j; k<j+randomNum; k++){
	    			memSize[k]=numb;
	    		}
//	    	    this.print();
	    	    this.check(numb,randomNum);
	    		return true;
	    	}
	    }
		return false;
	}
	
	private void deFragment(int fid, int size){
	//	int extra = MEMORY_CHUNK_SIZE - size;
		int begin = 0;
		int end = 0;
		for (int i=1; i<MEMORY_SIZE-1; i++){
			if (memSize[i]==0 && memSize[i-1]!=0){	//check for cases like 10,i.e., the empty space just after the last occupied element
				begin = i;
			}
			if (memSize[i]==0 && memSize[i+1]!=0){	//check cases like 01,i.e., the occupied space right after a free space
				end = i;
				break;
			}
		}
		int fragmentSize = end - begin + 1;	//size of the free memory in a chunk
		//int end = begin+extra-1;

		for (int i=begin; i<MEMORY_SIZE-fragmentSize; i++){	//shift the rest of the blocks until the free space is occupied
			memSize[i]=memSize[i+fragmentSize];
			memSize[i+fragmentSize] = 0;
		}
		
	//	this.check(fid,size);
	//	this.print();
	}
	
/**this method checks whether all the 0's of the array (unoccupied memory) is at the end. This shows that there is no fragmentation and file blocks are
	continuous. Eg. 111110000 would return true whereas 111100220003333 would return false (fragmentation)*/
	private Boolean check(int fid, int size){	
		int begin=0, end=0;
		for (int i=1; i<this.memSize.length-1; i++){
			if (memSize[i]==0 && memSize[i-1]!=0){
				begin = i;
			}
			if ((memSize[i]==0 && memSize[i+1]!=0) || (i==(this.memSize.length-2))){
				end = i;
				break;
			}
		}
	//	System.out.println(begin);
	//	System.out.println(end);
		if (end == this.memSize.length-2){
			return true;
		}
		else
			this.deFragment(fid, size);	//if all the free memory is not at the end, defragmentation is required 
			return false;
	}
	
	/**private void check(int fid, int size){
		int counter = 1;
		for (int i=0;i<MEMORY_SIZE; i++){
			if (this.memSize[i]==fid){
				counter++;
			}
		}
		if(counter==size)
			return;
		else{
			counter=1;
			for (int i=0;i<MEMORY_SIZE; i++){
				if (this.memSize[i]==fid){
					if(counter==size){
						this.setZero(i);
						return;
					}
					else
						counter++;
				}
			}
		}
			
	}
	
	private void setZero(int index){
		for (int j=index; j<MEMORY_SIZE; j++){
			this.memSize[j]=0;
		}
	}
	*/
	public void deallocateMemory(Node a){	//when file is closed
		int fid = a.getFID();
		int size = a.getSize();
		for (int i=0; i<this.memSize.length; i++){	//set those elements to 0 that contained the fid showing that the element is unoccupied
			if (this.memSize[i]==fid){
				memSize[i] = 0;
			}
		}
//		this.print();
		this.check(fid,size);
	}
	
	public void print(){	//print the memory array
		for (int i=0; i<this.memSize.length; i++){
			System.out.print(this.memSize[i]);
		}
		System.out.println("\n");
	}
}
