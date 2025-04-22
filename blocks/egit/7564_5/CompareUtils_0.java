		final String encoding = CompareCoreUtils.getResourceEncoding(baseFile);
		return getIndexTypedElement(repository, gitPath, encoding);
	}

	public static ITypedElement getIndexTypedElement(
			final Repository repository, final String repoRelativePath)
			throws IOException {
		String encoding = CompareCoreUtils.getResourceEncoding(repository, repoRelativePath);
		return getIndexTypedElement(repository, repoRelativePath, encoding);
	}
