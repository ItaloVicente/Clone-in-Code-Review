	private volatile int reconnectAttempt=1;
	private SocketChannel channel;
	private int toWrite=0;
	protected Operation optimizedOp=null;
	private volatile SelectionKey sk=null;
	private boolean shouldAuth=false;
	private CountDownLatch authLatch;
	private ArrayList<Operation> reconnectBlocked;
