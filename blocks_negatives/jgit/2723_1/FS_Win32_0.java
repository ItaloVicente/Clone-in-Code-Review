			gitPrefix = gitExe.getParentFile().getParentFile();
		else {
			String w = readPipe(userHome(), //
			                    new String[] { "bash", "--login", "-c", "which git" }, //
			                    Charset.defaultCharset().name());
			if (w != null)
				gitPrefix = new File(w).getParentFile().getParentFile();
		}
