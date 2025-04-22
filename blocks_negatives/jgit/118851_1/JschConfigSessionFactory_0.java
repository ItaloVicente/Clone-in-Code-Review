		try {
			final FileInputStream in = new FileInputStream(known_hosts);
			try {
				sch.setKnownHosts(in);
			} finally {
				in.close();
			}
