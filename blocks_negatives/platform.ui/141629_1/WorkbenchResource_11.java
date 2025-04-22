        if (name.equals(NAME)) {
            return SimpleWildcardTester.testWildcardIgnoreCase(value, res
                    .getName());
        } else if (name.equals(PATH)) {
            return SimpleWildcardTester.testWildcardIgnoreCase(value, res
                    .getFullPath().toString());
        } else if (name.equals(EXTENSION)) {
            return SimpleWildcardTester.testWildcardIgnoreCase(value, res
                    .getFileExtension());
        } else if (name.equals(READ_ONLY)) {
        } else if (name.equals(PROJECT_NATURE)) {
            try {
                IProject proj = res.getProject();
                return proj.isAccessible() && proj.hasNature(value);
            } catch (CoreException e) {
                return false;
            }
        } else if (name.equals(PERSISTENT_PROPERTY)) {
            return testProperty(res, true, false, value);
        } else if (name.equals(PROJECT_PERSISTENT_PROPERTY)) {
            return testProperty(res, true, true, value);
        } else if (name.equals(SESSION_PROPERTY)) {
            return testProperty(res, false, false, value);
        } else if (name.equals(PROJECT_SESSION_PROPERTY)) {
            return testProperty(res, false, true, value);
        } else if (name.equals(CONTENT_TYPE_ID)) {
            return testContentTypeProperty(res, value);
