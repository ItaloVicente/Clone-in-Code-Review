			if (err == null) {
				try {
					ftp.mkdir(path);
					return;
				} catch (IOException e) {
					err = e;
				}
			}
			throw new TransportException(MessageFormat.format(
						JGitText.get().cannotMkdirObjectPath
					err.getMessage())
