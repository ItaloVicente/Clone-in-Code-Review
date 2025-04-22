        if (testProject != null) {
            try {
                testProject.delete(true, null);
            } catch (CoreException e) {
                fail(e.toString());
            }
            testProject = null;
            testFolder = null;
            testFile = null;
        }
        super.doTearDown();
        navigator = null;
    }
