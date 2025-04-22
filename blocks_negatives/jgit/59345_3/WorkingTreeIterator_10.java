	private boolean mightNeedCleaning() {
		switch (getOptions().getAutoCRLF()) {
		case FALSE:
		default:
			return false;

		case TRUE:
		case INPUT:
			return true;
		}
