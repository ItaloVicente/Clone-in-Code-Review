		File tmpFile = File.createTempFile("._" + f.getName(), null, parentDir);
		WorkingTreeOptions opt = repo.getConfig().get(WorkingTreeOptions.KEY);
		FileOutputStream rawChannel = new FileOutputStream(tmpFile);
		OutputStream channel;
		if (opt.getAutoCRLF() == AutoCRLF.TRUE)
			channel = new AutoCRLFOutputStream(rawChannel);
		else
			channel = rawChannel;
		try {
			ol.copyTo(channel);
		} finally {
			channel.close();
		}
