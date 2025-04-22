		if (parseVersion(v) < makeVersion(2
			Map<String
			env.put("GIT_EDITOR"

			String w;
			try {
				w = readPipe(gitExe.getParentFile()
						new String[] { gitExe.getPath()
								"--edit" }
						SystemReader.getInstance().getDefaultCharset().name()
						env);
			} catch (CommandFailedException e) {
				LOG.warn(e.getMessage());
				return null;
			}
			if (StringUtils.isEmptyOrNull(w)) {
				return null;
			}
