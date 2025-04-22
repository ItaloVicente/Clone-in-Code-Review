		if (gitExe != null)
			return resolveGrandparentFile(gitExe);

		if (searchPath(path, "bash.exe") != null) { //$NON-NLS-1$
			String w = readPipe(userHome(),
					new String[] { "bash", "--login", "-c", "which git" }, // //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					Charset.defaultCharset().name());
			if (w != null) {
				gitExe = resolve(null, w);
				if (gitExe != null)
					return resolveGrandparentFile(gitExe);
