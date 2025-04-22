
	protected static String readPipe(File dir
		try {
			final Process p = Runtime.getRuntime().exec(command
			final BufferedReader lineRead = new BufferedReader(
					new InputStreamReader(p.getInputStream()
			String r = null;
			try {
				r = lineRead.readLine();
			} finally {
				p.getOutputStream().close();
				p.getErrorStream().close();
				lineRead.close();
			}

			for (;;) {
				try {
					if (p.waitFor() == 0 && r != null && r.length() > 0)
						return r;
					break;
				} catch (InterruptedException ie) {
				}
			}
		} catch (IOException e) {
		}
		return null;
	}
