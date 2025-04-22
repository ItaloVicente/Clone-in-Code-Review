	private boolean mightNeedCleaning(Entry entry) {
		switch (options.getAutoCRLF()) {
		case FALSE:
		default:
			return false;

		case TRUE:
		case INPUT:
			return true;
		}
	}

