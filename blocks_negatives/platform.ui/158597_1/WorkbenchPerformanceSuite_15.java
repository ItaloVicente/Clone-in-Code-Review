		addPerspectiveSwitchScenarios();
		addPerspectiveOpenCloseScenarios();
		addWindowOpenCloseScenarios();
		addContributionScenarios();
	}

	private void addContributionScenarios() {
		addTest(new ObjectContributionsPerformance(
				"large selection, limited contributors",
				ObjectContributionsPerformance.generateAdaptableSelection(
						ObjectContributionsPerformance.SEED, 5000),
				BasicPerformanceTest.NONE));
		addTest(new ObjectContributionsPerformance(
				"limited selection, limited contributors",
				ObjectContributionsPerformance.generateAdaptableSelection(
						ObjectContributionsPerformance.SEED, 50),
				BasicPerformanceTest.NONE));
	}

	private void addWindowOpenCloseScenarios() {
		for (String PERSPECTIVE_ID : PERSPECTIVE_IDS) {
			addTest(new OpenCloseWindowTest(PERSPECTIVE_ID, BasicPerformanceTest.NONE));
		}
	}

	private void addPerspectiveOpenCloseScenarios() {
		for (int i = 0; i < PERSPECTIVE_IDS.length; i++) {
			addTest(new OpenClosePerspectiveTest(PERSPECTIVE_IDS[i], i == 1 ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE));
		}
