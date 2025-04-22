		if (!(target instanceof IResource)) {
			return false;
		}
		IResource res = (IResource) target;
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
			return (res.isReadOnly() == value.equalsIgnoreCase("true"));//$NON-NLS-1$
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
		}
		return false;
	}

	private final boolean testContentTypeProperty(final IResource resource,
			final String contentTypeId) {
		final String expectedValue = contentTypeId.trim();

		if (!(resource instanceof IFile)) {
			return false;
		}

		final IFile file = (IFile) resource;
		String actualValue = null;

		try {
			final IContentDescription contentDescription = file
					.getContentDescription();

			if (contentDescription != null) {
				final IContentType contentType = contentDescription
						.getContentType();
				actualValue = contentType.getId();
			}
		} catch (CoreException e) {
		}

		return expectedValue == null || expectedValue.equals(actualValue);
	}

	private boolean testProperty(IResource resource, boolean persistentFlag,
			boolean projectFlag, String value) {
		String propertyName;
		String expectedVal;
		int i = value.indexOf('=');
		if (i != -1) {
			propertyName = value.substring(0, i).trim();
			expectedVal = value.substring(i + 1).trim();
		} else {
			propertyName = value.trim();
			expectedVal = null;
		}
		try {
			QualifiedName key;
			int dot = propertyName.lastIndexOf('.');
			if (dot != -1) {
				key = new QualifiedName(propertyName.substring(0, dot),
						propertyName.substring(dot + 1));
			} else {
				key = new QualifiedName(null, propertyName);
			}
			IResource resToCheck = projectFlag ? resource.getProject()
					: resource;
			if (resToCheck == null) {
				return false;
			}
			if (persistentFlag) {
				String actualVal = resToCheck.getPersistentProperty(key);
				if (actualVal == null) {
					return false;
				}
				return expectedVal == null || expectedVal.equals(actualVal);
			}

			Object actualVal = resToCheck.getSessionProperty(key);
			if (actualVal == null) {
