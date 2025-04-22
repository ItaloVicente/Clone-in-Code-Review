	private boolean certifyMode(int mode) {
		switch (mode) {
		case VIEW_ACTIVATE:
		case VIEW_VISIBLE:
		case VIEW_CREATE:
			return true;
		default:
			return false;
		}
	}
