#!/bin/bash

############################
# Check if no parameters passed
############################

if [ "$#" -lt 1 ]; then
    echo "No Paramaters Provided"
	exit
fi

############################
# Check if Hadoop is working
############################

jpscount=`jps | grep -P " NameNode| DataNode| SecondaryNameNode| ResourceManager| NodeManager| JobHistoryServer" | wc -l`

if [ $jpscount != 6 ]; then
	echo "Please check if yarn, dfs and job historyserver are running"
	exit
fi


############################
# Set Global Variables
############################

INPUT_DIR=../data/books
FILES=$INPUT_DIR/*.txt
JAR_PATH=../data/jar

HDFS_PATH="/17200430"
HDFS_BOOKS="${HDFS_PATH}/books"

############################
# Create directory (local + hdfs)
############################

#Input Directory
if [ ! -d "$INPUT_DIR" ]
then
	printf "\n\nMaking Input Directory . . .\n\n"
	mkdir $INPUT_DIR
else 
	printf "Input Directory Exists . . .\n\n"
fi

# Function to check and Create Hadoop Directory
checkCreate(){
	printf "Checking Hadoop Directory ${1}. . .\n\n"

	hdfs dfs -test -d $1
	if [ $? != 0 ];	then
		printf "\n\nCreating Directory in HDFS . . .\n\n"
		hdfs dfs -mkdir $1
	else
		printf "\n\nDirectory already present in HDFS . . .\n\n"
	fi
}
 
# Create User Directory
checkCreate $HDFS_PATH

# Create books directory
checkCreate $HDFS_BOOKS



############################
# Download Books and Copy to Hdfs
############################

printf "Downloading books . . .\n\n"
wget -i ../data/url.txt -NP $INPUT_DIR/

printf "Copying Books to HDFS . . . \n\n"
hdfs dfs -copyFromLocal -f $FILES $HDFS_BOOKS/



############################
# Function Definition 
# for different Questions
############################

function executeQ1(){
	printf "Question 1:\n"
	printf "\nWhat is the number of distinct words in the corpus?\nHow many words start with the letter Z/z?\nHow many words appear less than 4 times?\n"
	printf "\nRemoving old output directories . . . \n\n"
        hdfs dfs -rm -r $HDFS_PATH/output-q1

        printf "\nRunning Map Reduce Task, Please Wait . . . \n\n"
        hadoop jar $JAR_PATH/Q1.jar $HDFS_BOOKS/*.txt $HDFS_PATH/output-q1/
}

function executeQ2(){
	printf "Question 2:\n"
	printf "\nhow many terms appear in only one single document?  Such words may  appear  multiple times in one document, but they have to appear in only one document in the corpus\n"
	
	printf "\nRemoving old output directories . . . \n\n"
        hdfs dfs -rm -r $HDFS_PATH/output-q2

        printf "\nRunning Map Reduce Task, Please Wait . . . \n\n"
        hadoop jar $JAR_PATH/Q2.jar $HDFS_BOOKS/*.txt $HDFS_PATH/output-q2/

}

function executeQ3(){
	printf "\n\nQuestion 3:\n"
	printf "Enter stopword:"
	read stopword

	printf "\nFinding most frequent words after stopword: ${stopword} \n"
	
	printf "\nRemoving old output directories . . . \n\n"
	hdfs dfs -rm -r $HDFS_PATH/output-q3

	printf "\nRunning Map Reduce Task, Please Wait . . . \n\n"
	hadoop jar $JAR_PATH/Q3.jar $HDFS_BOOKS/*.txt $HDFS_PATH/output-q3/ $stopword
	printf "\n\nReading Output from file:\n\n"
	hdfs dfs -cat $HDFS_PATH/output-q3/part-r-00000
}

############################
# Parse Parameters and Execute Functions
############################

for var in "$@"
do
	if [ "$var" = "q1" ];then
		executeQ1
	fi
	if [ "$var" = "q2" ];then	
		executeQ2
	fi
	if [ "$var" = "q3" ];then
		executeQ3
	fi
done

