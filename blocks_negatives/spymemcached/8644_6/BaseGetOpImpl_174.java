	private static final OperationStatus END = new OperationStatus(true, "END");
	private static final OperationStatus NOT_FOUND = new OperationStatus(false, "NOT_FOUND");
	private static final byte[] RN_BYTES = "\r\n".getBytes();
	private final String cmd;
	private final Collection<String> keys;
	private String currentKey = null;
	private final int exp;
	private final boolean hasExp;
	private long casValue=0;
	private int currentFlags = 0;
	private byte[] data = null;
	private int readOffset = 0;
	private byte lookingFor = '\0';
	private boolean hasValue;

	public BaseGetOpImpl(String c,
			OperationCallback cb, Collection<String> k) {
		super(cb);
		cmd=c;
		keys=k;
		exp=0;
		hasExp=false;
		hasValue = false;
	}
