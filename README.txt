Please see till below, it contain two parts: 
1. CLI for Big Data
2. Hadoop

------------------------------------------------------
Part 1:  CLI for Big Data
------------------------------------------------------

1.1. Prerequisites: 
-----------------------------------------
	1. Linux Distribution
	2. Change directory to cli-bash
	
		cd cli-bash		#Change directory to bash-scripts
	
	3. Give rights to the executable run-cli.sh file 
	   which will perform all further activities
	     
		chmod u+x ./run-cli.sh	#Give Execuatble Permissions


1.2. General Instructions to execute script:
-----------------------------------------	
	To execute the script just pass the argument [question code] 
	to the bash file while executing it. 
	The script can accept multiple arguments (maximum 4 arguments).  
	
	The general syntax to execute script:
	
		./run-cli.sh  <argument1  optional [argument2  argument 3  argument 4]>
	
        Kindly Note: Arguments are separated by spaces


1.3. Valid Arguments: Question Codes
-----------------------------------------
        For question1:		q1
        For question2:		q2
        For question3:		q3
	For question4:		q4
	

1.4. Commands to execute questions:
-----------------------------------------
	Question 1: What is the number of distinct words in the corpus?  
	how many words start with the letter Z/z?  
	how many words appear less than 4 times?
	
		./run-cli.sh q1		#Command to execute question 1
	
	Question 2: What are the most frequent words that end in "ing" ?
	
		./run-cli.sh q2		#Command to execute question 2
	
	Question 3: Which is more frequent:  me/my/mine/I or us/our/ours/we?
	
		./run-cli.sh q3		#Command to execute question 3
	
	Question 4: Take one stopword (e.g., the, and) and compute the 
	five words that appear the most after it.  
	E.g.  ”the cat belongs to the old lady from the hamlet”
	 → ”cat ”, ”old” and ”hamlet” would be candidates.
	The output should contains 5 lines with the words and their frequency.
	
		./run-cli.sh q4		#Command to execute question 4
		
		A stopword will be required for this section 
		and it will then promt you to enter the stopword.

1.5. Alternatively, you can run multiple questions at a single time:	
-----------------------------------------
For example:			
	Ex 1: For question 1 and 2

		./run-cli.sh q1 q2		#Excutes question 1 and 2
		
	Ex 2: For all the questions
		
		./run-cli.sh q1 q2 q3 q4	#Excutes question 1, 2, 3 and 4



------------------------------------------------------
Part 2:  HADOOP 
------------------------------------------------------

2.1. Prerequisites:
-----------------------------------------
        1. Linux Distribution
	2. Already set up HADOOP - According to the manual provided
            Using jps to detect if everything is working or not
        3. Change directory to hadoop

                cd hadoop                 #Change directory to hadoop

        3. Give rights to the executable run-hadoop.sh file 
	   which will perform all the activities

                chmod u+x ./run-hadoop.sh	#Give Execuatble Permissions


2.2. General Instructions to execute script:
-----------------------------------------
        To execute the script just pass the argument [question code] 
	to the bash file while executing it. 
	The script can accept multiple arguments (maximum 3 arguments).
        
	The general syntax to execute script:

        	./run-hadoop.sh  <argument1  optional [argument2  argument 3] >

        Kindly Note: Arguments are separated by spaces


2.3. Valid Arguments: Question Codes
-----------------------------------------
        For question1:      q1
        For question2:      q2
        For question3:      q3


2.4. Commands to execute questions:
-----------------------------------------
        Question 1: What is the number of distinct words in the corpus?
        how many words start with the letter Z/z?
        how many words appear less than 4 times?

                ./run-hadoop.sh q1             #Command to execute question 1

        Question 2: What are the most frequent words that end in "ing" ?

                ./run-hadoop.sh q2             #Command to execute question 2

        Question 3: Which is more frequent:  me/my/mine/I or us/our/ours/we?

                ./run-hadoop.sh q3             #Command to execute question 3


2.5. Alternatively, you can run multiple questions at a single time:	
-----------------------------------------
For example:	
	Ex 1: For question 1 and 2

		./run-hadoop.sh q1 q2		#Excutes question 1 and 2
		
	Ex 2: For all the questions
		
		./run-hadoop.sh q1 q2 q3	#Excutes question 1, 2, 3


2.6. Extra Information
-----------------------------------------
Jar files are stored in ./data/jar folder
Source code for Map Reduce is saved in ./data/source-code



