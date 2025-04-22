	private String getUriFilterPath() {
		String filterPath = null;
		if (!uriText.getText().isEmpty()) {
			try {
				File testFile = new File(uriText.getText());
				if (testFile.exists()) {
					filterPath = testFile.getPath();
				} else {
					URIish testUri = new URIish(uriText.getText());
					if (Protocol.FILE.defaultScheme
							.equals(testUri.getScheme())) {
						testFile = new File(testUri.getPath());
						if (testFile.exists()) {
							filterPath = testFile.getPath();
						}
					}
				}
			} catch (IllegalArgumentException | URISyntaxException e) {
			}
		}
		if (filterPath == null || filterPath.isEmpty()) {
			filterPath = RepositoryUtil.getDefaultRepositoryDir();
		}
		return filterPath;
	}

