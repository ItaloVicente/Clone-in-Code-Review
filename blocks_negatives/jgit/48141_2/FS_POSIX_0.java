			String w = readPipe(userHome(), //
					new String[] { "bash", "--login", "-c", "which git" }, // //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					Charset.defaultCharset().name());
			if (w == null || w.length() == 0)
				return null;
			return resolveGrandparentFile(new File(w));
