# CPUEmulator

Emulation of my own made up CPU architecture since I can't spend the time to learn one.

the actual program can be found in Examples/guess.wor. It includes strings that were employed but that I did not include in the read me.

Sample input file:

    ;-GET TARGET DATA
    RNDRANGE rbx7d 0x00 0x09
    PRINT rbx7d
    
    ;-GET USER INPUT
    PRINTREG rbx00 rbx11
    INPUT rbx7e
    PLACE rbx7c 0x30
    SUB rbx7e rbx7c
    CASTBW rwx0000 rbx7d
    CASTBW rwx0001 rbx7e
    CMPW rwx0000 rwx0001 rwx00b8 rwx00be
    PRINTREG rbx22 rbx31
    JMPW rwx0079
    PRINTREG rbx12 rbx21
    JMPW rwx009c

Corresponding output:
    
     Guess the	 
    num
    ber:
    You were wrong .!
    "Y#o$u% &w'e(r)e* +r,i-g.h/t0!1
    (} 	}% !~|0~|'  }' ~   � � %"1 y %! � 

Onscreen output at runtime:
    
    Guess the number:
    6
    You were wrong.
    Guess the number:
    2
    You were wrong.
    Guess the number:
    8
    You were wrong.
    Guess the number:
    7
    You were right!
