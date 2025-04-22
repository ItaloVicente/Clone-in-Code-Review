		if (indexVersion == -1)
			indexVersion = db.getConfig().get(CoreConfig.KEY)
					.getPackIndexVersion();
		final BufferedInputStream in;
		final org.eclipse.jgit.transport.IndexPack ip;
		in = new BufferedInputStream(System.in);
		ip = new org.eclipse.jgit.transport.IndexPack(db, in, base);
		ip.setFixThin(fixThin);
		ip.setIndexVersion(indexVersion);
		ip.index(new TextProgressMonitor());
