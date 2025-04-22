		File logfile = new File(db.getDirectory()
		if (!logfile.getParentFile().mkdirs()
				&& !logfile.getParentFile().isDirectory()) {
			throw new IOException(
					"oops
							+ logfile);
		}
		try (OutputStream fileOutputStream = Files
				.newOutputStream(logfile.toPath())) {
			fileOutputStream.write(data);
		}
	}
