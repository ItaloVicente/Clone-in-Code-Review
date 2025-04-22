	private void assertContainsEntryWithMode(String zipFilename
			throws Exception {
		final File cwd = db.getWorkTree();
		final ProcessBuilder procBuilder = new ProcessBuilder("zipinfo"
				.redirectErrorStream(true);
		Process proc = null;
		try {
			proc = procBuilder.start();
		} catch (IOException e) {
			assumeNoException(e);
		}

		proc.getOutputStream().close();
				new InputStreamReader(proc.getInputStream()
		try {
			String line;
			while ((line = reader.readLine()) != null)
				if (line.startsWith(mode) && line.endsWith(name))
					return;
			fail("expected entry " + name + " with mode " + mode + " but found none");
		} finally {
			proc.getOutputStream().close();
			proc.destroy();
		}
	}

	private void writeRaw(String filename
			throws IOException {
		final File path = new File(db.getWorkTree()
		final OutputStream out = new FileOutputStream(path);
		try {
			out.write(data);
		} finally {
			out.close();
		}
	}

