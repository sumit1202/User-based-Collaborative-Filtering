# User-based-Collaborative-Filtering

User-based Collaborative Filtering using Cosine similarity


1. Compile and Run Recommender.java file

    [a.] javac recommender.java
    
    [b.] java Recommender
 

 
2. For running java file, following files were used-    

   Input files: [i] ratingsx.csv {userId, movieId, rating, timestamp}
   
               [ii] toBeRated.csv {userId, movieId}
 
 After running java file, following file was generated-
 
 Output file: [i] finalResult.csv { userId, movieId and opRating }
 
              [ii]sortedResult.csv { userId, movieId and opRating }
              
                 -calculated similarity sorted in descending order for each user.

 

#Pseudo code-

1.	Start
2.	In class Recommender, Initialize a createMatrix() which reads ratingsx.csv file and forms matrix based on data provided by input file.
3.	Initialize a testData() which reads toBeRatedx.csv file and forms matrix based on data provided by given file.
4.	Initialize cosineSimilarityUser() with parameters int[][] matrix and int[][] test  which will give calculated similarity i.e. opRating[i].
5.	In main, call createMatrix(), testData(), and cosineSimilarityUser() respectively which leads to printing of calculated similarity i.e. opRating[i] and, finally writing it to finalResult.csv file using FileWriter.
6.	End.
                          

 

                           

 






