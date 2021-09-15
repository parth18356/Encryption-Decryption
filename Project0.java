import java.util.*;

public class Project0{
	
	public static int hashLen= 0;

	public static void fillMatrix(int[][] matrix, String input){
		int row= 0, col= matrix[0].length;
		int pos= 0;
		char[] inputArr= input.toCharArray();
		for(char c: inputArr){
			if(pos== col){ 
				pos= 0;
				row++;
			}
			matrix[row][pos]= (int)c- 65;
			pos++; 
		}
	}

	public static String hashGenerator(String input, int hashLen){
		int n= input.length(), rows= 0;
		if(n%hashLen!= 0) rows= (n/hashLen)+ 1;
		else rows= (n/hashLen);
		int[][] matrix= new int[rows][hashLen];
		fillMatrix(matrix, input);
		int[] hashBits= new int[hashLen];
		for(int i=0; i<hashLen; i++){
			for(int j=0; j<rows; j++){
				hashBits[i]^= matrix[j][i];
				hashBits[i]%=3;
			}
		}
		String hashCode= "";
		for(int i: hashBits) hashCode+= String.valueOf((char)(65+i));
		return hashCode;
	}

	public static String cipheredText(String input, HashMap<String, String> map){
	
		String output= "";
		int n= input.length();
		for(int i=0; i<n; i+=2){
			char c1= input.charAt(i);
			if((i+1)< n){
				char c2= input.charAt(i+1);
				output+= map.get(String.valueOf(c1)+ String.valueOf(c2));
			}
			else output+= String.valueOf(c1);
		}
		return output;
	}

	public static String decipheredText(String input, boolean[] flag, HashMap<String, String> map){
		
		int n= input.length();
		String output= "";
		for(int i=0; i<n; i+=2){
			char c1= input.charAt(i);
			if((i+1)< n){
				char c2= input.charAt(i+1);
				output+= map.get(String.valueOf(c1)+ String.valueOf(c2));
			}
			else output+= String.valueOf(c1);
		}
		
		String hashCode= output.substring(n- hashLen);
		output= output.substring(0, n-hashLen);
		n-= hashLen;

		if(hashCode.equals(hashGenerator(output, hashLen))) flag[0]= true;
		
		return output;
	}


	public static void mapped(HashMap<Character, Character> map, HashMap<String, String> map1, HashMap<String, String> map2){
		for(int i=0; i<3; i++){
			char val1= (char)(65+i);
			String key= "", cipher= "";
			for(int j=0; j<3; j++){
				char val2= (char)(65+j);
				key= String.valueOf(val1)+ String.valueOf(val2);
				cipher= String.valueOf(map.get(val1))+ String.valueOf(map.get(val2));
				map1.put(key, cipher);
				map2.put(cipher, key);
			}
		}
	}

	public static void mapKeys(int val, HashMap<Character, Character> map, HashMap<String, String> map1, HashMap<String, String> map2){
		for(int i=0; i<3; i++){
			char key= (char)(65+i);
			int newVal= (i+val)%3;
			char mappedTo= (char)(65+ newVal);
			map.put(key, mappedTo);
		}
		mapped(map, map1, map2);
	}

	public static HashMap<String, String> launchAttack(String input){

		HashMap<Character, Character> map= new HashMap<>();
		HashMap<String, String> map1= new HashMap<>();
		HashMap<String, String> map2= new HashMap<>();
		for(int i=0; i<3; i++){ // Will generate all the combinations of the key
			boolean[] flag= {false};
			mapKeys(i, map, map1, map2);
			decipheredText(input, flag, map2);
			if(flag[0]) return map2;
		}
		return null;
	}

	public static void input(int val, String message){
		if(message.length()%2== 0) hashLen= 4;
		else hashLen= 5;
		Scanner sc= new Scanner(System.in);
		HashMap<Character, Character> map= new HashMap<>();
		HashMap<String, String> map1= new HashMap<>();
		HashMap<String, String> map2= new HashMap<>();
		mapKeys(val, map, map1, map2);

		System.out.println("KEY USED FOR ENCRYPTION: "+ map1);
		System.out.println("KEY USED FOR DECRYPTION: "+ map2);

		System.out.println();

		String input= message;
		String hashCode= hashGenerator(input, hashLen);

		System.out.println(hashCode);

		input+= hashCode;
		String output= cipheredText(input, map1);
	
		System.out.println("CIPHERED TEXT: "+ output);		

		String cipher= output;
		output= decipheredText(output, new boolean[]{false}, map2);
		
		System.out.println();

		HashMap<String, String> foundKeys= launchAttack(cipher);
		System.out.println("DECRYPTION KEYS FOUNDED AFTER BRUTE FORCE: ");
		System.out.println(foundKeys);
		System.out.println();
	}


	public static void main(String[] args) {
		input(2, "AAACCCABCABCCAACBAACBCBC");
		input(1, "CBBCABBACBABBBACBABCBCBCBCBABCBCBBAC");
		input(0, "ACBABBCABCBBACBBBACBB");
		input(1, "CBCABCBABBAACABBACBBBCBBCCABCBCBABAAABAAA");
		input(2, "BABABABBABABCBCBCBCABABBACBCBCA");
	}
}