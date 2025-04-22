		List<String> expect = new ArrayList<String>();
		expect.add("core.filemode=" + !isWindows);
		expect.add("core.logallrefupdates=true");
		if (isMac)
			expect.add("core.precomposeunicode=true");
		expect.add("core.repositoryformatversion=0");
		assertArrayEquals("expected default configuration"
				output);
