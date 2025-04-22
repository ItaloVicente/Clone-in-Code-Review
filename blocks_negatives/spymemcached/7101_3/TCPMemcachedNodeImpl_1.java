	private final SocketAddress socketAddress;
	private final ByteBuffer rbuf;
	private final ByteBuffer wbuf;
	protected final BlockingQueue<Operation> writeQ;
	private final BlockingQueue<Operation> readQ;
	private final BlockingQueue<Operation> inputQueue;
