		if (gitExe != null)
			return gitExe.getParentFile().getParentFile();

		if (SystemReader.getInstance().isMacOS()) {
			String w = readPipe(userHome(), //
					new String[] { "bash", "--login", "-c", "which git" }, // //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					Charset.defaultCharset().name());
			if (w == null || w.length() == 0)
				return null;
			File parentFile = new File(w).getParentFile();
			if (parentFile == null)
				return null;
			return parentFile.getParentFile();
