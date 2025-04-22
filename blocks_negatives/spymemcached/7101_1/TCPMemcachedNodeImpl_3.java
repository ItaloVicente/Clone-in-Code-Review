
	private final AtomicInteger continuousTimeout = new AtomicInteger(0);


	public TCPMemcachedNodeImpl(SocketAddress sa, SocketChannel c,
			int bufSize, BlockingQueue<Operation> rq,
			BlockingQueue<Operation> wq, BlockingQueue<Operation> iq,
			long opQueueMaxBlockTime, boolean waitForAuth, long dt) {
		super();
		assert sa != null : "No SocketAddress";
		assert c != null : "No SocketChannel";
		assert bufSize > 0 : "Invalid buffer size: " + bufSize;
		assert rq != null : "No operation read queue";
		assert wq != null : "No operation write queue";
		assert iq != null : "No input queue";
		socketAddress=sa;
		setChannel(c);
		rbuf=ByteBuffer.allocate(bufSize);
		wbuf=ByteBuffer.allocate(bufSize);
		getWbuf().clear();
		readQ=rq;
		writeQ=wq;
		inputQueue=iq;
