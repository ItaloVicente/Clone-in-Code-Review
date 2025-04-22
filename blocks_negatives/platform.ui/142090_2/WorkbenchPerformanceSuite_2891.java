     *
     */
    private void addWindowOpenCloseScenarios() {
        for (int i = 0; i < PERSPECTIVE_IDS.length; i++) {
            addTest(new OpenCloseWindowTest(PERSPECTIVE_IDS[i], BasicPerformanceTest.NONE));
        }
    }

    /**
     *
     *
     */
    private void addPerspectiveOpenCloseScenarios() {
        for (int i = 0; i < PERSPECTIVE_IDS.length; i++) {
            addTest(new OpenClosePerspectiveTest(PERSPECTIVE_IDS[i], i == 1 ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE));
        }
    }

    /**
     *
     */
    private void addPerspectiveSwitchScenarios() {
        for (int i = 0; i < PERSPECTIVE_SWITCH_PAIRS.length; i++) {
            addTest(new PerspectiveSwitchTest(PERSPECTIVE_SWITCH_PAIRS[i], BasicPerformanceTest.NONE));
        }
    }

    private void addLayoutScenarios() {
    }

    public static String[] getAllPerspectiveIds() {
