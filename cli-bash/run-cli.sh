#!/bin/bash

if [ "$#" -lt 1 ]; then
    echo "No Paramaters Provided"
	exit
fi

############################
# Step 1: Global Variables
############################

INPUT_DIR=../data/books

FILES=$INPUT_DIR/*.txt
TEMP=./temp.txt

############################
# Step 2: Create input directory
############################

if [ ! -d "$INPUT_DIR" ]
then
	printf "\n\nMaking Input Directory .. "
	mkdir $INPUT_DIR
else 
	printf "\n\nInput Directory Exists .. "
fi

############################
# Step 3: Download Books
############################

printf "\n\nDownloading books .. "
wget -i ../data/url.txt -NP $INPUT_DIR/

############################
# Step 3: Tokenize Words and 
#         save in temp location
############################

sed '/^[[:space:]]*$/d;s/[[:space:]]*$//;s/--/ /g;s/[—‘’”“•´-]//g;s/^\xEF\xBB\xBF//g' $FILES | tr -s '[:punct:][:blank:]\r\n' '\n'| tr '[:upper:]' '[:lower:]' > $TEMP
printf "\n\n"

############################
# Step 4: Function Definition 
#         for different Questions
############################

function executeQ1(){

	printf "Question 1:\n"

	printf "Total Number of Distinct Words:"
	cat $TEMP | sort | uniq | wc -l
	
	printf "Number of Words that start with Z/z:"
	cat $TEMP | sort | uniq | grep "^[zZ]" | wc -l
	
	printf "Number of Words appearing less than 4 times:"
	cat $TEMP | sort | uniq -c | awk 'BEGIN{ sum=0} {if ($1 < 4) sum=sum+1} END {print sum}'
	printf "\n"

}

function executeQ2(){
	
	printf "Question 2:\n"
	printf "Top 10 Words ending with 'ing':\n"
	cat $TEMP | sort -f	 | uniq -ci | grep "ing$" | sort -nr | head -10
	printf "\n"

}

function executeQ3(){
	
	printf "Question 3:\n"
	printf "More frequent: me/my/mine/I or us/our/ours/we:"
	cat $TEMP | sort -f | uniq -ci | awk 'BEGIN{ sum1=0; sum2=0;} {if($2 ~ /^me$|^my$|^mine$|^i$/ ) sum1=sum1+$1; else if($2 ~ /^us$|^our$|^ours$|^we$/) sum2=sum2+$1} END{ if (sum1 > sum2) printf "\nme/my/mine/I are more frequent Count:%d",sum1; else printf "\nus/our/ours/we are more frequent Count:%d",sum2;}'
	printf "\n\n"

}


function executeQ4(){
	
	printf "Question 4:\n"
	
	#Prompt for parameters
	printf "Enter stopword:"
	read stopword
	stopword=`echo $stopword | tr '[:upper:]' '[:lower:]'`
	printf "\nMost frequent words after '"$stopword"':\n"
	
	sed '/^[[:space:]]*$/d;s/[[:space:]]*$//;s/--/ /g;s/[—‘’”“•´-]//g;s/^\xEF\xBB\xBF//g' $FILES | tr '\r\n' '\n' | tr -s '[:punct:][:blank:]' ' ' | tr '[:upper:]' '[:lower:]' | awk '{ for(i = 1; i < NF; i++) { if($i=="'$stopword'") {i=i+1;print $i;} } }' | sort | uniq -c | sort -nr | head -5
	
	printf "\n"

}

############################
# Step 5: Parse Parameters and Execute Functions
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
	if [ "$var" = "q4" ];then
		executeQ4
	fi
done

############################
# Step 6: Remove temp file
############################

rm $TEMP
