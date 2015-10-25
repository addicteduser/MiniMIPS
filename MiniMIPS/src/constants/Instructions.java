package constants;

public enum Instructions {
	DADDU, DMULT, OR, SLT, // R-type Instructions
	DSLL, // R-type (Shift)
	ADDS, MULS, // Extended R-type Instructions
	BEQ, LW, LWU, SW, ANDI, DADDIU, LS, SS, // I-type Instructions
	J // J-type Instructions
}
