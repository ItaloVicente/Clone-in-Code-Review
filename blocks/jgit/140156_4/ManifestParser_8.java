								MessageFormat.format(
										RepoText.get().errorIncludeFile
								e);
					}
				} else if (filename != null) {
					int index = filename.lastIndexOf('/');
					String path = filename.substring(0
					try (InputStream is = new FileInputStream(path)) {
						read(is);
					} catch (IOException e) {
						throw new SAXException(
								MessageFormat.format(
										RepoText.get().errorIncludeFile
								e);
