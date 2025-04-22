	private static final int ID_SZ = 20;
	private static final int TYPE_SHIFT = 12;
	private static final int TYPE_TREE = 0040000 >>> TYPE_SHIFT;
	private static final int TYPE_SYMLINK = 0120000 >>> TYPE_SHIFT;
	private static final int TYPE_FILE = 0100000 >>> TYPE_SHIFT;
	private static final int TYPE_GITLINK = 0160000 >>> TYPE_SHIFT;

