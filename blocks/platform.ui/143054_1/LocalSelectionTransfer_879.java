	private static final String TYPE_NAME = "local-selection-transfer-format" + Long.valueOf(System.currentTimeMillis()).toString(); //$NON-NLS-1$;

	private static final int TYPEID = registerType(TYPE_NAME);

	private static final LocalSelectionTransfer INSTANCE = new LocalSelectionTransfer();

	private ISelection selection;

	private long selectionSetTime;

	protected LocalSelectionTransfer() {
	}

	public static LocalSelectionTransfer getTransfer() {
		return INSTANCE;
	}

	public ISelection getSelection() {
		return selection;
	}

	private boolean isInvalidNativeType(Object result) {
		return !(result instanceof byte[])
				|| !TYPE_NAME.equals(new String((byte[]) result));
	}

	@Override
