	private static final String A = "aaa\n";
	private static final String B = "bbbbb\nbb\nbbb\n";
	private static final String C = "c\n";
	private static final String D = "dd\n";
	private static final String E = "ee\n";
	private static final String F = "fff\nff\n";
	private static final String G = "gg\n";
	private static final String H = "h\nhhh\nhh\n";
	private static final String I = "iiii\n";
	private static final String J = "jj\n";
	private static final String Z = "zzz\n";
	private static final String Y = "y\n";

	private static final String XXX_0 = "<<<<<<< O\n";
	private static final String XXX_1 = "=======\n";
	private static final String XXX_2 = ">>>>>>> T\n";

	String base=A+B+C+D+E+F+G+H+I+J;

	String replace_C_by_Z=A+B+Z+D+E+F+G+H+I+J;
	String replace_A_by_Y=Y+B+C+D+E+F+G+H+I+J;
	String replace_A_by_Z=Z+B+C+D+E+F+G+H+I+J;
	String replace_J_by_Y=A+B+C+D+E+F+G+H+I+Y;
	String replace_J_by_Z=A+B+C+D+E+F+G+H+I+Z;
	String replace_BC_by_ZZ=A+Z+Z+D+E+F+G+H+I+J;
	String replace_BCD_by_ZZZ=A+Z+Z+Z+E+F+G+H+I+J;
	String replace_BD_by_ZZ=A+Z+C+Z+E+F+G+H+I+J;
	String replace_BCDEGI_by_ZZZZZZ=A+Z+Z+Z+Z+F+Z+H+Z+J;
	String replace_CEFGHJ_by_YYYYYY=A+B+Y+D+Y+Y+Y+Y+I+Y;
	String replace_BDE_by_ZZY=A+Z+C+Z+Y+F+G+H+I+J;
	String delete_C=A+B+D+E+F+G+H+I+J;

