
		Map<String
		env.put("GIT_EDITOR"

		String w = readPipe(gitExe.getParentFile()
				new String[] { "git"
				Charset.defaultCharset().name()
		if (StringUtils.isEmptyOrNull(w)) {
			return null;
		}

		return new File(w);
