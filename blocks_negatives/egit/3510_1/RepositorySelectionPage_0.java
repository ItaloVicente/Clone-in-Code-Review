						URI testUri = URI.create(uriText.getText().replace(
								'\\', '/'));
							String path = testUri.getPath();
								path = path.substring(1);

							dialog.setFilterPath(path);
