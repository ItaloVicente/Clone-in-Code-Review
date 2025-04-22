	public PackConfig clone() {
		try {
			return (PackConfig) super.clone();
		} catch (CloneNotSupportedException cannotClone) {
			throw new RuntimeException(cannotClone);
		}
	}

