	public enum Sha1Implementation {
		JAVA(SHA1Java.class)
		JDKNATIVE(SHA1Native.class);

		private final String implClassName;

		private Sha1Implementation(Class implClass) {
			this.implClassName = implClass.getName();
		}
