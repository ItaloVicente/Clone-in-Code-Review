
	protected void setupReflog(String logName, byte[] data)
			throws FileNotFoundException, IOException {
				File logfile = new File(db.getDirectory(), logName);
				if (!logfile.getParentFile().mkdirs()
						&& !logfile.getParentFile().isDirectory()) {
					throw new IOException(
							"oops, cannot create the directory for the test reflog file"
									+ logfile);
				}
				FileOutputStream fileOutputStream = new FileOutputStream(logfile);
				try {
					fileOutputStream.write(data);
				} finally {
					fileOutputStream.close();
				}
			}

