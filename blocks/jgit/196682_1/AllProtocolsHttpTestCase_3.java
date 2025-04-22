	@ParameterizedTest(name = "{0}")
	@MethodSource("allProtocols")
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface TestAllProtocols {
	}

	public static Collection<Arguments> allProtocols() {
