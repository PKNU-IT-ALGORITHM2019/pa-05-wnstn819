import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class pa05 {
	static int count=0;
	static int countD=1;
	static ArrayList<String> arr = new ArrayList<String>();
	static ArrayList<String> arrD = new ArrayList<String>();
    static String[][] max = null;
	public static Node root;
	public pa05() {
		this.root = null;
	}
	static String[][] read(ArrayList<String> arr) throws IOException {
		try {
		BufferedReader br = new BufferedReader(new FileReader("shuffled_dict.txt")); //파일 한줄씩 다 읽기.
		arr.add(new String(br.readLine()));
	    	while( br.ready()) {
			
				arr.add(new String(br.readLine()));
		     }
			arr.add(null);
			
			br.close();
			 String[][] mvp = new String[size(arr)][1000];
				for(int i=0; i<size(arr);i++) {
					String[] arrplus = arr.get(i).split("\\(");
				
				for(int j=0; j<arrplus.length;j++) {
				
					mvp[i][j] = arrplus[j].trim();
					
				}
				
				}
				return mvp;
				
				
			
		} catch (FileNotFoundException e1) {
			System.out.println("파일을 찾을 수 없습니다.");
			e1.printStackTrace();
			return null;
		}
		
		
	}
	static ArrayList<String> readD(ArrayList<String> arr) throws IOException {
		try {
		BufferedReader br = new BufferedReader(new FileReader("to_be_deleted_words.txt")); //파일 한줄씩 다 읽기.
		arr.add(new String(br.readLine()));
		
	    	while( br.ready()) {
			
				arr.add(new String(br.readLine()));
				countD++;
		     }
			arr.add(null);
			
			br.close();
			 
			return arr;
				
			
		} catch (FileNotFoundException e1) {
			System.out.println("파일을 찾을 수 없습니다.");
			e1.printStackTrace();
			return null;
		}
		
		
	}
	
     static int size(ArrayList<String> arr) {
		
	    while(arr.get(count)!=null) {
		  count++;
	    }
		return count;
	}
	
	public boolean find(String id) {
		Node current = root;
		while (current != null) {
			if (current.word.compareToIgnoreCase(id)==0) {
				System.out.println("("+current.text);
				return true;
			} else if (id.compareToIgnoreCase(current.word)<0 ) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
	    System.out.println("Not found.");
		return false;
	}
	

	public boolean delete(String id) {
		
		Node parent = root;
		Node current = root;
		boolean isLeftChild = false;
	while (id.compareToIgnoreCase(current.word) !=0) {
			parent = current;
			if (id.compareToIgnoreCase(current.word)<0) {
				isLeftChild = true;
				current = current.left;
			} else {
				isLeftChild = false;
				current = current.right;
			}
			if (current == null) {
				System.out.println("Not found");
				return false;
			}
		}
		if (current.left == null && current.right == null) {
			if (current == root) {
				root = null;
			}
			if (isLeftChild == true) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		}

		else if (current.right == null) {
			if (current == root) {
				root = current.left;
			} else if (isLeftChild) {
				parent.left = current.left;
			} else {
				parent.right = current.left;
			}
		}
		else if (current.left == null) {
			if (current == root) {
				root = current.right;
			} else if (isLeftChild) {
			parent.left = current.right;
			} else {
				parent.right = current.right;
			}
		} else if (current.left != null && current.right != null) {
          
			Node successor = getSuccessor(current);
			if (current == root) {
				root = successor;
			} else if (isLeftChild) {
				parent.left = successor;
			} else {
				parent.right = successor;
			}

		}
		System.out.println("Deleted successfully");
		return true;
	}
	public Node getSuccessor(Node deleleNode) {
		Node successsor = null;
		Node successsorParent = null;
		Node current = deleleNode.right;
		while (current != null) {
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}

		if (successsor != deleleNode.right) {
			successsorParent.left = successsor.right;
			successsor.right = deleleNode.right;
		}
		return successsor;
	}
	public void insert(String id,String text) {
		Node newNode = new Node(id,text);
		if (root == null) {
			root = newNode;
			return;
		}
		Node current = root;
		Node parent = null;
		while (true) {
			parent = current;
			if (id.compareToIgnoreCase(current.word)<0) {
				
				current = current.left;
				if (current == null) {
					parent.left = newNode;
					return;
				}
			} else {
				
				current = current.right;
				if (current == null) {
					parent.right = newNode;
					return;
				}
			}
		}
	}
	public void display(Node root) {
		if (root != null) {
			display(root.left);
			System.out.print(" " + root.word);
			display(root.right);
		}
	}
	public static void main(String arg[]) throws IOException {
		pa05 b = new pa05();
		Scanner sc = new Scanner(System.in);
		max = read(arr);
		
		  for(int i =0; i<count;i++) { b.insert(max[i][0], max[i][1]); }
		 
		
	    
         for( ; ;) {
			
			
			System.out.print("$ ");
			String name = sc.next(); //일단 한 단어 입력 받고
			
			if(name.equals("size")) { //name이 read랑 같은지
				System.out.println(count);
				
			}else if(name.equals("find")) {
				
				String emt = sc.nextLine().trim();
			  	b.find(emt);
				
								
			}else if(name.equals("add")) {
				
				System.out.print("word: ");
				String emt = sc.next();
				
				System.out.print("class: ");
				String emtt = sc.next();
				sc.nextLine();
				System.out.print("meaning: ");
				String emttt = sc.nextLine();
				
				b.insert(emt, emtt+") "+emttt);
				
			}else if(name.equals("deleteall")) {
				readD(arrD);
				for(int i=0; i<countD;i++) {
					b.delete(arrD.get(i));
					System.out.println(i + arrD.get(i));
				}
				System.out.println(countD+" words were deleted successfully.");
				
				
				
			}else if(name.equals("delete")) {
				String emt = sc.nextLine().trim();
				b.delete(emt);
			}
			else if(name.contains("exit")) {
				return;
			}
			
		}
         
		
	}
	
}
class Node {
	String word;
	String text;
	Node left;
	Node right;
	public Node(String word, String text) {
		this.word = word;
		this.text = text;
		left = null;
		right = null;
	}
}


////////
//파일의 내용을 pa01을 참고해서 전부 배열에 넣고
//word만 이진검색 트리에 저장
//
