		}

		return accelerator;
	}

	private static final IKeyFormatter NATIVE_FORMATTER = new NativeKeyFormatter();

	public static IKeyFormatter getKeyFormatterForPlatform() {
		return NATIVE_FORMATTER;
	}

	private static char topKey(Event event) {
		char character = event.character;
		boolean ctrlDown = (event.stateMask & SWT.CTRL) != 0;

		if (ctrlDown && event.character != event.keyCode && event.character < 0x20) {
