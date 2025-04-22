	private final CASResponse casResponse;

	public CASOperationStatus(boolean success, String msg, CASResponse cres) {
		super(success, msg);
		casResponse=cres;
	}
