	public static enum OperationType {
		CHECKOUT_OP

		CHECKIN_OP
	}

	private OperationType operationType = OperationType.CHECKOUT_OP;

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

