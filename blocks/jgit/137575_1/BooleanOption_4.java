	public boolean toBoolean() {
		switch (this) {
		case True:
		case notDefinedTrue:
			return true;
		default:
			return false;
		}
