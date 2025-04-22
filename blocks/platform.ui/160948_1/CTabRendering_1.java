	@Override
	public void setCornerShape(String cornerShape) {
		try {
			this.cornerShape = CornerShape.valueOf(cornerShape);
		} catch (IllegalArgumentException ex) { /* Do nothing */}
	}

