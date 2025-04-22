		} else
			isDirectory = path.toFile().isDirectory();

		StringBuilder b = new StringBuilder('/');
		b.append(path.lastSegment());
		if (isDirectory)
			b.append('/');
		b.append('\n');
		String entry = b.toString();
