
	protected List<File> getCeilings() {
		return Collections.singletonList(trash.getParentFile().getAbsoluteFile());
	}

	private void ceilTestDirectories(List<File> ceilings) {
		mockSystemReader.setProperty(Constants.GIT_CEILING_DIRECTORIES_KEY
	}

	private String makePath(List<?> objects) {
		final StringBuilder stringBuilder = new StringBuilder();
		for (Object object : objects) {
			if (stringBuilder.length() > 0)
				stringBuilder.append(File.pathSeparatorChar);
			stringBuilder.append(object.toString());
		}
		return stringBuilder.toString();
	}

