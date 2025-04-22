    /**
     * Sets the test harness.
     *
     * @param testHarness the test harness
     */
    public void setTestHarness(ITestHarness testHarness) {
        Assert.isNotNull(testHarness);
        this.testHarness = testHarness;
    }
