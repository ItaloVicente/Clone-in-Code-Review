	public static SHA1 newInstance() {
						.equalsIgnoreCase(
								Sha1Implementation.JDKNATIVE.name())) {
			return new SHA1Native();
		}
		return new SHA1Java();
