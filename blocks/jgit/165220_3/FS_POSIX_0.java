				}
			}
			if (gitExe != null
					&& DEFAULT_GIT_LOCATION.equals(gitExe.getPath())) {
				try {
					String w = readPipe(userHome()
							new String[] { "xcode-select"
							Charset.defaultCharset().name());
					if (StringUtils.isEmptyOrNull(w)) {
						gitExe = null;
					} else {
						File realGitExe = new File(new File(w)
								DEFAULT_GIT_LOCATION.substring(1));
						if (!realGitExe.exists()) {
							gitExe = null;
						}
