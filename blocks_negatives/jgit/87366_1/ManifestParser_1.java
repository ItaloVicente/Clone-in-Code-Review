			}
			if (is == null) {
				throw new SAXException(
						RepoText.get().errorIncludeNotImplemented);
			}
			try {
				read(is);
			} catch (IOException e) {
				throw new SAXException(e);
