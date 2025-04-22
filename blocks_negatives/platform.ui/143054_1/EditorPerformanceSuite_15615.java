    /**
     *
     */
    private void addOpenCloseScenarios() {
        for (int i = 0; i < EDITOR_FILE_EXTENSIONS.length; i++) {
            addTest(new OpenCloseEditorTest(EDITOR_FILE_EXTENSIONS[i], i == 3 ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE));
        }
    }
