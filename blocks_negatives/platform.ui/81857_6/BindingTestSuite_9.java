	/**
	 * @param testCase
	 *            TODO
	 * @return true if the given test is temporarily disabled
	 */
	public static boolean failingTestsDisabled(TestCase testCase) {
		System.out.println("Ignoring disabled test: "
				+ testCase.getClass().getName() + "." + testCase.getName());
		return true;
