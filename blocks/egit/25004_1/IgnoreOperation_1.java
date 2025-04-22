		} else
			isDirectory = path.toFile().isDirectory();

		StringBuilder b = new StringBuilder("/" + path.lastSegment()); //$NON-NLS-1$
		if (isDirectory)
			b.append('/');
		b.append('\n');
		String entry = b.toString();
