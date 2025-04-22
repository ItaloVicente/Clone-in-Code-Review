		try {
			SubmoduleValidator.assertValidSubmoduleName(path);
			SubmoduleValidator.assertValidSubmodulePath(path);
			SubmoduleValidator.assertValidSubmoduleUri(uri);
		} catch (SubmoduleValidator.SubmoduleValidationException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

