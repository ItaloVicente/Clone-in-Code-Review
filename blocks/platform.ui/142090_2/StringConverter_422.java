	public static String asString(Point value) {
		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.x);
		buffer.append(',');
		buffer.append(value.y);
		return buffer.toString();
	}
