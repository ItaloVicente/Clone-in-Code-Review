				if (remote == null) {
					if (defaultRemote == null) {
						if (filename != null)
							throw new SAXException(MessageFormat.format(
									RepoText.get().errorNoDefaultFilename
									filename));
						else
							throw new SAXException(
									RepoText.get().errorNoDefault);
					}
