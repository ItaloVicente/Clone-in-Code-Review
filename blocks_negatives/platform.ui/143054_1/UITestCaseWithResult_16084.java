        TestResult result;
        try {
            result = new TestResult(performTest());
        } catch (Throwable t) {
            result = new TestResult(t);
        }
