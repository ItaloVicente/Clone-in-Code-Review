		File logfile = new File(db.getDirectory()
		if (!logfile.getParentFile().mkdirs()
				&& !logfile.getParentFile().isDirectory()) {
			throw new IOException(
					"oops
							+ logfile);
		}
		FileOutputStream fileOutputStream = new FileOutputStream(logfile);
		try {
			fileOutputStream.write(data);
		} finally {
			fileOutputStream.close();
		}
	}
