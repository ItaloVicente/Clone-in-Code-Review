				try (InputStream is = includedReader.readIncludeFile(name)) {
					if (is == null) {
						throw new SAXException(
								RepoText.get().errorIncludeNotImplemented);
					}
					read(is);
