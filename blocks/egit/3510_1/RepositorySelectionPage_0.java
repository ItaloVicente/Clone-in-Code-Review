						File testFile = new File(uriText.getText());
						if (testFile.exists())
							dialog.setFilterPath(testFile.getPath());
						else {
							URIish testUri = new URIish(uriText.getText());
							if (testUri.getScheme().equals(
									Protocol.FILE.defaultScheme)) {
								testFile = new File(uri.getPath());
								if (testFile.exists())
									dialog.setFilterPath(testFile.getPath());
							}
