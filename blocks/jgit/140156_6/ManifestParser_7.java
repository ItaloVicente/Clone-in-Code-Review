				break;
				if (includedReader != null) {
					try (InputStream is = includedReader
							.readIncludeFile(name)) {
						if (is == null) {
							throw new SAXException(
									RepoText.get().errorIncludeNotImplemented);
						}
						read(is);
					} catch (Exception e) {
