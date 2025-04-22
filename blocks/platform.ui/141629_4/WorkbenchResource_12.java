        switch (name) {
        case NAME:
        	return SimpleWildcardTester.testWildcardIgnoreCase(value, res
        			.getName());
        case PATH:
        	return SimpleWildcardTester.testWildcardIgnoreCase(value, res
        			.getFullPath().toString());
        case EXTENSION:
        	return SimpleWildcardTester.testWildcardIgnoreCase(value, res
        			.getFileExtension());
        case READ_ONLY:
        	return (res.isReadOnly() == value.equalsIgnoreCase("true"));//$NON-NLS-1$
        case PROJECT_NATURE:
        	try {
        		IProject proj = res.getProject();
        		return proj.isAccessible() && proj.hasNature(value);
        	} catch (CoreException e) {
        		return false;
        	}
        case PERSISTENT_PROPERTY:
        	return testProperty(res, true, false, value);
        case PROJECT_PERSISTENT_PROPERTY:
        	return testProperty(res, true, true, value);
        case SESSION_PROPERTY:
        	return testProperty(res, false, false, value);
        case PROJECT_SESSION_PROPERTY:
        	return testProperty(res, false, true, value);
        case CONTENT_TYPE_ID:
        	return testContentTypeProperty(res, value);
        default:
        	break;
