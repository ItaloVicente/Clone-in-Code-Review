				if (!uriText.getText().isEmpty()) {
					try {
						File testFile = new File(uriText.getText());
						if (testFile.exists()) {
							dialog.setFilterPath(testFile.getPath());
						} else {
							URIish testUri = new URIish(uriText.getText());
							if (Protocol.FILE.defaultScheme
									.equals(testUri.getScheme())) {
								testFile = new File(testUri.getPath());
								if (testFile.exists()) {
									dialog.setFilterPath(testFile.getPath());
								}
							}
						}
					} catch (IllegalArgumentException | URISyntaxException e) {
					}
				}
				String filterPath = dialog.getFilterPath();
				if (filterPath == null || filterPath.isEmpty()) {
					dialog.setFilterPath(
							RepositoryUtil.getDefaultRepositoryDir());
				}
