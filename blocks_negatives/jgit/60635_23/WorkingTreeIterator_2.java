	private boolean mightNeedCleaning() throws IOException {
		switch (getOptions().getAutoCRLF()) {
		case FALSE:
		default:
			if (getCleanFilterCommand() != null)
				return true;
			return false;

		case TRUE:
		case INPUT:
			return true;
		}
	}

