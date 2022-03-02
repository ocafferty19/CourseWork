#! /bin/bash
filename=$1

if [ $# -lt 2 ]
#if the number of arguments is less than 2, usage txt.
#If the second arg is not a file, state its not a tar file.
then 
	echo "Usage: ./tarzam.sh filename tarfile "
	exit 1
elif	[ ! -f $2 ]
then
	echo "Cant find tar file $2"
	exit 1
fi

#c=`tar -tf $2 | grep  '/'${filename}'$'`

c=`tar -t -f $2|grep $1`
#echo "file name is : $filename"
#echo "c is:   $c"
#c holds the data for the search if arg1(file) is in arg2(tarfile).
if [ $c : != :$1 ]
then 
	echo "$1 does not exist in $2"
	exit
else 
	echo " $1 is in $2" 
fi

