	private static final int REBUILD_INDEX_COUNT = 1000;

	private Entry head;

	private Entry free;

	private int inQueue;

	private int sinceLastIndex;

	private Entry[] index;

	private int first;

	private int last = -1;
