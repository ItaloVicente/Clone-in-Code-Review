			throw new ConfigInvalidException(MessageFormat
					.format(JGitText.get().cannotReadFile
		}
	}

	private File getIncludedFile(String path) {
		File file = new File(path);
			File userHome = FS.DETECTED.userHome();
			if (userHome != null) {
				file = new File(userHome
			}
