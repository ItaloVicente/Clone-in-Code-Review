	private static final int CONTINUE = 0;
	private static final int HAVE_ALL = 1;
	private static final int CONTINUE_ON_STACK = 2;

	private int carryOntoOne(RevCommit p
		int rc = (p.flags & carry) == carry ? HAVE_ALL : CONTINUE;
