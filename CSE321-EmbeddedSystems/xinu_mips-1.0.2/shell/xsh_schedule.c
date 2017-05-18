#include <kernel.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#define NFRAME 6
#define NFRAME2 10
#define NSLOT 2
#define SLOT_T 5000

int tps,slot=0,frame=0;


void one() {
  printf("Task 1 running\n");
  sleep(1);
}

void two() {
  printf("Task 2 running\n");
  sleep(1);
}

void three() {
  printf("Task 3 running\n");
  sleep(1);
}

void four() {
  printf("Task 4 running\n");
  sleep(1);
}

void burn() { printf ("brn cycle \n ");}

void (*ttable[NFRAME][NSLOT])() = {
{one, one},
{two, three},
{one, one},
{two, burn},
{one, one},
{burn, burn}};

void (*ttable2[NFRAME2][NSLOT])() = {
{one, three},
{two, two},
{one, burn},
{two, two},
{one, burn},
{two, two},
{one, burn},
{four, four},
{two, two},
{one, burn}};

command xsh_schedule(ushort stdout, ushort stdin, ushort stderr, ushort nargs, char *args[])
{
        char str[10];
        char *str1 = "1";
        char *str2 = "2";
        printf("Enter 1 or 2 to get table 1 or table 2 respectively \n");
        scanf("%s", str);
        int value1 = strcmp(str,str1);
        int value2 = strcmp(str,str2);
        if(value1 == 0){
    while (1) {
      printf("\n Starting a new hyperperiod (Hyperperiod = 12)\n");
      for (frame=0; frame <NFRAME; frame++)
        { printf ("\n Starting a new frame (Frame Size = 2)\n");
                for (slot=0; slot<NSLOT; slot++)
                (*ttable[frame][slot])();
        }}
        }
	else if (value2 == 0){
    while (1) {
      printf("\n Starting a new hyperperiod (Hyperperiod = 20)\n");
      for (frame=0; frame <NFRAME2; frame++)
        { printf ("\n Starting a new frame (Frame Size = 2)\n");
                for (slot=0; slot<NSLOT; slot++)
                (*ttable2[frame][slot])();
        }}
	}

}
