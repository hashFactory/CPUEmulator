# CPUEmulator

Emulation of my own made up CPU architecture since I can't spend the time to learn one.

Sample input file:

    PUSH 0x58
    POP 0x00
    PLACE 0x03 0x16
    NOR 0x02 0x03
    PRINT 0x02
    PRINTHEX 0x02

Corresponding output:
    
    X 

Onscreen output at runtime:
    
    Program Data: 
    0x02 0x58 0x03 0x00 0x06 0x03 0x16 0x0e 0x02 0x03 0x07 0x02 0x10 0x02
    Register 2: -95
    Register 2: 0xa1
