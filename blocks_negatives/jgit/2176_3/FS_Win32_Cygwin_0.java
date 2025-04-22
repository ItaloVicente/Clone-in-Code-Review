		try {
			final Process p;

			p = Runtime.getRuntime().exec(
					new String[] { cygpath, "--windows", "--absolute", pn },
					null, dir);
			p.getOutputStream().close();

			final BufferedReader lineRead = new BufferedReader(
					new InputStreamReader(p.getInputStream(), "UTF-8"));
			String r = null;
			try {
				r = lineRead.readLine();
			} finally {
				lineRead.close();
			}

			for (;;) {
				try {
					if (p.waitFor() == 0 && r != null && r.length() > 0)
						return new File(r);
					break;
				} catch (InterruptedException ie) {
				}
			}
		} catch (IOException ioe) {
		}
