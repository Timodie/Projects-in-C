#include <stdio.h>
#include "csapp.h"


int mul(int arg1,int arg2){
	return arg1*arg2;
}


int main(){

int client;
 client = Open_clientfd("elnux1.cs.umass.edu", 48579);
if(client > 0){
	 printf("Connected to server\n");
 							}
FILE*fp;
            fp = Fdopen(client, "a+");
            printf("Sending spire id to server\n");
            fprintf(fp,"0000000");

char *line = NULL;
size_t count;
getline(&line,&count,fp);

printf("Printing line and count to output :\n");
printf("%s \n",line);
char*save = line;


int arg1, arg2;
 count = sscanf(save, "MUL%d%d", &arg1,&arg2);


printf("printing operands : %d %d\n",arg1,arg2);

int answer = mul (arg2,arg1);

printf("Answer is :%d\n",answer );
printf("Now sending answer to server\n");

//allocate space for string conversion
char*ans = (char*)malloc(256);
//convert int to string
sprintf(ans, "%d", answer);

free(line);
line = NULL;

//send to server
fprintf(fp,"%s",ans);

getline(&line,&count,fp);

printf("Printing feedback \n");

printf("%s\n",line);

free(line);
line =NULL;

//Sending bonus
printf("Sending Bonus : HW9 \n");
//Actual send
fprintf(fp,"HW9");
//Receiving
getline(&line,&count,fp);
//Printing feedback
printf("%s\n",line);

free(line);
line=NULL;

//free(count);

//Connection closed
printf("Disconnected from server\n");
 Close(client);
//close file desc
 fclose(fp);
//free answer
free(ans);

return 0;
}
