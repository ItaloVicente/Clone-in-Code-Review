final class MutatorOperationImpl extends OperationImpl
	implements MutatorOperation {

	public static final int OVERHEAD=32;

	private static final OperationStatus NOT_FOUND=
		new OperationStatus(false, "NOT_FOUND");

	private final Mutator mutator;
	private final String key;
	private final int amount;

	public MutatorOperationImpl(Mutator m, String k, int amt,
			OperationCallback c) {
		super(c);
		mutator=m;
		key=k;
		amount=amt;
	}

	@Override
	public void handleLine(String line) {
		getLogger().debug("Result:  %s", line);
		OperationStatus found=null;
		if(line.equals("NOT_FOUND")) {
			found=NOT_FOUND;
		} else {
			found=new OperationStatus(true, line);
		}
		getCallback().receivedStatus(found);
		transitionState(OperationState.COMPLETE);
	}

	@Override
	public void initialize() {
		int size=KeyUtil.getKeyBytes(key).length + OVERHEAD;
		ByteBuffer b=ByteBuffer.allocate(size);
		setArguments(b, mutator.name(), key, amount);
		b.flip();
		setBuffer(b);
	}

	@Override
	protected void wasCancelled() {
		getCallback().receivedStatus(CANCELLED);
	}

	public Collection<String> getKeys() {
		return Collections.singleton(key);
	}

	public int getBy() {
		return amount;
	}

	public long getDefault() {
		return -1;
	}

	public int getExpiration() {
		return -1;
	}

	public Mutator getType() {
		return mutator;
	}

