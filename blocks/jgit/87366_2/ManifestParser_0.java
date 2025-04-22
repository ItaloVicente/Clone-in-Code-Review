			try {
				if (includedReader != null) {
					try {
						is = includedReader.readIncludeFile(name);
					} catch (Exception e) {
						throw new SAXException(MessageFormat.format(
									RepoText.get().errorIncludeFile
					}
				} else if (filename != null) {
					int index = filename.lastIndexOf('/');
					String path = filename.substring(0
					try {
						is = new FileInputStream(path);
					} catch (IOException e) {
						throw new SAXException(MessageFormat.format(
									RepoText.get().errorIncludeFile
					}
				}
				if (is == null) {
					throw new SAXException(
							RepoText.get().errorIncludeNotImplemented);
