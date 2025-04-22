			if (inputStream == null) {
				if (path == null || path.length() == 0)
					throw new IllegalArgumentException(
							JGitText.get().pathNotConfigured);
				try {
					inputStream = new FileInputStream(path);
				} catch (IOException e) {
					throw new IllegalArgumentException(
							JGitText.get().pathNotConfigured);
				}
			}
