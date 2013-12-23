#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
   setuid( 0 );
   system( "/home/ovswitch/Documents/startOVSwitch.sh" );

   return 0;
}

