	public void layoutIfNecessary() {
		if (dirtySize != null && control instanceof Composite) {
			if (control.getSize().equals(dirtySize)) {
				((Composite) control).layout(flushChildren);
				flushChildren = false;
			}
		}
		dirtySize = null;
	}
