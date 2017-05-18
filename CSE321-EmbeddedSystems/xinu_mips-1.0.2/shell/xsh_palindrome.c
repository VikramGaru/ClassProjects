#include <stdio.h>
#include <shell.h>
#include <kernel.h>
#include <string.h>
command xsh_palindrome(ushort stdout, ushort stdin, ushort stderr, ushort nargs, char *args[]){
char input[512];
printf("Enter a string\n");
scanf("%s",&input);
int len=strlen(&input);
int i=0;
for(i=0;i<len;i++){
if(input[i]!=input[len-i-1]){
printf("NOT PALINDROME\n");
return OK;
}
}
printf("PALINDROME\n");
return OK;
}
