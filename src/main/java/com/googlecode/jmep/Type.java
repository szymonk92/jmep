package com.googlecode.jmep;

public enum Type {
    MRK(0),  // Start/End of expression
    OPA(1),  // Open parentheses           (
    FNC(2),  // Function call              f(
    CMA(3),  // Comma                      ,
    UNA(4),  // Unary operator             -x
    BIN(5),  // Binary operator            x+y
    VAL(6),  // Value                      1.2
    VAR(7),  // Variable                   a
    CPA(8),  // Close parentheses          )
    ERR(9),  // Syntax Error
    UNI(10), // Unit operator              mm
    ;

    final int index;

    private Type(int index) {
        this.index = index;
    }
}
