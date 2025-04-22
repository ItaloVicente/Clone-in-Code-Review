    /**
     *
     */
    private void addSwitchScenarios() {
        for (int i = 0; i < EDITOR_SWITCH_PAIRS.length; i++) {
            addTest(new EditorSwitchTest(EDITOR_SWITCH_PAIRS[i]));
        }
    }
