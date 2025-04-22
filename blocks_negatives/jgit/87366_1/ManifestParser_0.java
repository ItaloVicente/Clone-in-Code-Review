			if (includedReader != null) {
				try {
					is = includedReader.readIncludeFile(name);
				} catch (Exception e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile, name), e);
