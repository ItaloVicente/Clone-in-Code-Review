		OutputStream channel = new FileOutputStream(tmpFile);
		if (opt.getAutoCRLF() == AutoCRLF.TRUE)
			channel = new AutoCRLFOutputStream(channel);
		if (smudgeFilterCommand != null) {
			ProcessBuilder filterProcessBuilder = fs
					.runInShell(smudgeFilterCommand, new String[0]);
