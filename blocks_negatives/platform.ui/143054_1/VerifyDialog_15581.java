    /*
     * Create an instance of the verification dialog.
     */
    public VerifyDialog(Shell parent) {
        super(parent);
        if (!(TEST_TYPE <= 2) && !(TEST_TYPE >= 0)) {
            TEST_TYPE = TEST_SIZING;
        }
        _failureText = "";
        _dialogTests[0] = new SizingTestPass();
        _dialogTests[1] = new FocusTestPass();
        _dialogTests[2] = new AccessibilityTestPass();
    }
