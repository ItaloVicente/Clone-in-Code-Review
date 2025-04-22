				break;
				if (includedReader != null) {
					try (InputStream is = includedReader.readIncludeFile(name)) {
						if (is == null) {
							throw new SAXException(
									RepoText.get().errorIncludeNotImplemented);
						}
						read(is);
					} catch (Exception e) {
						throw new SAXException(MessageFormat.format(
								RepoText.get().errorIncludeFile
					}
				} else if (filename != null) {
					int index = filename.lastIndexOf('/');
					String path = filename.substring(0
					try (InputStream is = new FileInputStream(path)) {
						read(is);
					} catch (IOException e) {
						throw new SAXException(MessageFormat.format(
								RepoText.get().errorIncludeFile
