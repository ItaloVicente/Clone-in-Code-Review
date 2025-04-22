        TestResult result;
        try {
            result = new TestResult(test.performTest());
        } catch (Throwable t) {
            result = new TestResult(t);
        }
