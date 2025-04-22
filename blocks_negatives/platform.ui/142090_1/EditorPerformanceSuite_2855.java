    /**
     *
     */
    private void addOpenMultipleScenarios(boolean closeAll) {
        for (int i = 0; i < EDITOR_FILE_EXTENSIONS.length; i++) {
            addTest(new OpenMultipleEditorTest(EDITOR_FILE_EXTENSIONS[i], closeAll, BasicPerformanceTest.NONE));
        }
    }
