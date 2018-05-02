import java.util.*;
import java.io.*;
import java.lang.Math;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



class Node
{
	public Integer u,m;
	public Double r;
	Node(int uu,int mm,double rr){u=new Integer(uu);m=new Integer(mm);r=new Double(rr);};
}

class RatingComparator implements Comparator<Node>
{
	
	public int compare(Node n1,Node n2)
	{
		//System.out.println("n1.u: "+n1.u+"n2.u "+n2.u);
		if(n1.u.compareTo(n2.u)==0)
		{
			//System.out.println("n1.r: "+n1.r+"n2.r "+n2.r);
			return -n1.r.compareTo(n2.r);
		}
			
		return n1.u.compareTo(n2.u);
	}
}

class Recommender {
	public static int[][] CreateMatrix() throws FileNotFoundException, IOException {
		// Initialize the matrix with -1 for all elements
		int[][] matrix = new int[6040][3952];
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[0].length; ++j) {
				matrix[i][j] = -1;
			}
		}

		// Read the input values and form the full matrix 
		BufferedReader br = new BufferedReader(new FileReader("ratingsx.csv"));//read csv file
		StringTokenizer st = null; //for breaking strings into tokens
		String row;

		while ((row = br.readLine()) != null) {
			st = new StringTokenizer(row, ",");//Constructs a string tokenizer for the specified string 
												//and tokens separated by delim [,]
			while (st.hasMoreTokens()) {
				int user = Integer.parseInt(st.nextToken());
				int movie = Integer.parseInt(st.nextToken());
				int rating = Integer.parseInt(st.nextToken());
				matrix[user - 1][movie - 1] = rating;
				st.nextToken();
			}

		}
		//System.out.println("matrix created");
		return matrix;

	}

	public static int[][] testData() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("toBeRatedx.csv"));
		StringTokenizer st = null;
		String row;
		int[][] data = new int[9430][2];
		int i = 0;
		while ((row = br.readLine()) != null) {
			st = new StringTokenizer(row, ",");
			while (st.hasMoreTokens()) {
				data[i][0] = Integer.parseInt(st.nextToken());
				data[i][1] = Integer.parseInt(st.nextToken());
			}
			i += 1;
		}
		//System.out.println("data created");
		return data;
	}
	
	
	
	public static float[] CosineSimilarityUser(int[][] matrix, int[][] test) throws FileNotFoundException, IOException
	{
		int len = test.length;
		int lenUsers = matrix.length;
		int lenMovies = matrix[0].length;
		int user = 0;
		int movie = 0;
		float[] opRating = new float[len];

		for (int i = 0; i < len; ++i) {
			user = test[i][0];
			movie = test[i][1];
			float upperNum = 0;
			float upperDenom = 0;
			for (int j = 0; j < lenUsers; ++j) {
				if (matrix[j][movie - 1] != -1) {
					float num = 0;
					float denom1 = 0;
					float denom2 = 0;
					boolean flag = false;
					for (int k = 0; k < lenMovies; ++k) {
						if ((matrix[user - 1][k] != -1) && (matrix[j][k] != -1)) {
							flag = true;
							num += (float) matrix[user - 1][k] * matrix[j][k];
							denom1 += (float) matrix[user - 1][k] * matrix[user - 1][k];
							denom2 += (float) matrix[j][k] * matrix[j][k];
						}
					}
					if (flag) {
						upperDenom += num / (Math.sqrt(denom1 * denom2));
						upperNum += matrix[j][movie - 1] * num / (Math.sqrt(denom1 * denom2));
					}
				}
			}

			float predrating = 0;

			if (upperDenom > 0) {
				predrating = upperNum / upperDenom;
			} else {
				predrating = upperNum;
			}
			opRating[i] = predrating;
		}
		return opRating;
	}

	public static void main(String args[]) throws IOException
	{
		System.out.println("Recommendation System Ratings !!");
		//String UserItem = args[0];
		//String SimPCJ = args[1];
		int[][] matrix = CreateMatrix();
		int[][] test = testData();

		float[] opRating = new float[test.length];

		//if (UserItem.equals("User")) {
			//switch (SimPCJ) {
			//case "Cosine":
				System.out.println("User Cosine");
				opRating = CosineSimilarityUser(matrix, test);
				//break;

			//}
		//}

		/*if (UserItem.equals("User")) {
			PrintWriter writer = new PrintWriter("result.csv", "UTF-8");
			for (int i = 0; i < opRating.length; ++i) {
				writer.println(test[i][0]);
				writer.println(test[i][1]);
				writer.println(opRating[i]);

			}
			writer.close();
		}

		PrintWriter writer = new PrintWriter("matrix.csv", "UTF-8");
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[0].length; ++j) {
				writer.println(matrix[i][j]);
			}
			writer.close();
		}*/

		FileWriter fw = new FileWriter("finalResult.csv");
		PrintWriter out = new PrintWriter(fw);
		// ',' divides the word into columns
		for (int i = 0; i < opRating.length; ++i) {
			out.print(test[i][0]);// first row first column
			out.print(",");
			out.print(test[i][1]);// first row second column
			out.print(",");
			out.println(opRating[i]);// first row third column

			//Flush the output to the file
			out.flush();
		}
		//Close the Print Writer
		out.close();

		//Close the File Writer
		fw.close();
    
// Sorting final result file in descending order according to cosineSim	
	 ArrayList<Node> rowlist=new ArrayList<Node>();
	 BufferedReader reader = new BufferedReader(new FileReader("finalResult.csv"));
     StringTokenizer st = null;
	String row;
	while ((row = reader.readLine()) != null) 
		{
			st = new StringTokenizer(row, ",");
			rowlist.add(new Node(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Double.parseDouble(st.nextToken())));
			//System.out.println(rowlist.get(rowlist.size()-1).u+"  "+rowlist.get(rowlist.size()-1).m+" "+rowlist.get(rowlist.size()-1).r);
		}
	Collections.sort(rowlist,new RatingComparator());  
	//for(int i=0; i<rowlist.size(); i++){System.out.println(rowlist.get(i).u+"  "+rowlist.get(i).m+" "+rowlist.get(i).r);}}
	fw = new FileWriter("sortedResult.csv");
	out = new PrintWriter(fw);
		// ',' divides the word into columns
	for (int i = 0; i < opRating.length; ++i) {
			out.print(rowlist.get(i).u);// first row first column
			out.print(",");
			out.print(rowlist.get(i).m);// first row second column
			out.print(",");
			out.println(rowlist.get(i).r);// first row third column

			//Flush the output to the file
			out.flush();
		}
		//Close the Print Writer
		out.close();

		//Close the File Writer
		fw.close();
	}
}