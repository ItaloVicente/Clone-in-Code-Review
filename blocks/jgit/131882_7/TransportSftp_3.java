			if (err == null) {
				try {
					return ftp.put(path);
				} catch (IOException e) {
					err = e;
				}
			}
			throw new TransportException(
					MessageFormat.format(JGitText.get().cannotWriteObjectsPath
							objectsPath
					err);
