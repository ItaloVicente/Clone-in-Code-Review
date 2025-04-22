		if (gitExe != null)
			return resolveGrandparentFile(gitExe);

		if (SystemReader.getInstance().isMacOS()) {
			if (searchPath(path, "bash") != null) { //$NON-NLS-1$
				String w = readPipe(userHome(),
						new String[] { "bash", "--login", "-c", "which git" }, // //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						Charset.defaultCharset().name());
				if (w == null || w.length() == 0)
					return null;
				gitExe = new File(w);
				return resolveGrandparentFile(gitExe);
