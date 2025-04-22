
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj instanceof E4PartWrapper) {
			E4PartWrapper wrapper = (E4PartWrapper) obj;
			if (wrapper.wrappedPart == wrappedPart) {
				return true;
			}
			if (wrapper.wrappedPart != null) {
				return wrapper.wrappedPart.equals(wrappedPart);
			}
		}
		return false;
	}
