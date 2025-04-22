	private void addWindowOpenCloseScenarios() {
		for (String PERSPECTIVE_ID : PERSPECTIVE_IDS) {
			addTest(new OpenCloseWindowTest(PERSPECTIVE_ID, BasicPerformanceTest.NONE));
		}
	}

	private void addPerspectiveOpenCloseScenarios() {
		for (int i = 0; i < PERSPECTIVE_IDS.length; i++) {
			addTest(new OpenClosePerspectiveTest(PERSPECTIVE_IDS[i], i == 1 ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE));
		}
	}

	private void addPerspectiveSwitchScenarios() {
		for (String[] PERSPECTIVE_SWITCH_PAIR : PERSPECTIVE_SWITCH_PAIRS) {
			addTest(new PerspectiveSwitchTest(PERSPECTIVE_SWITCH_PAIR, BasicPerformanceTest.NONE));
		}
	}

	private void addLayoutScenarios() {
	}

	public static String[] getAllPerspectiveIds() {
