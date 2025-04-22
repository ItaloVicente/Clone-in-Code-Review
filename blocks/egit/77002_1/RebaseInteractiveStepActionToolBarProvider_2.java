	private String textFor(ElementAction action, String base) {
		int accelerator = getActionAccelerators().get(action).intValue();
		return base + " (" + SWTKeySupport.getKeyFormatterForPlatform().format( //$NON-NLS-1$
				SWTKeySupport.convertAcceleratorToKeyStroke(accelerator)) + ')';
	}

	private String textFor(int idx, String base) {
		int accelerator = getMoveAccelerators()[idx];
		return base + " (" + SWTKeySupport.getKeyFormatterForPlatform().format( //$NON-NLS-1$
				SWTKeySupport.convertAcceleratorToKeyStroke(accelerator)) + ')';
	}

