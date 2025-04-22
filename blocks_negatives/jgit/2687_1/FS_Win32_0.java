			return gitExe.getParentFile().getParentFile();

		String w = readPipe(userHome(), //
				new String[] { "bash", "--login", "-c", "which git" }, //
				Charset.defaultCharset().name());
		if (w != null)
			return new File(w).getParentFile().getParentFile();
