		List<String> expect = new ArrayList<>();
		expect.add("gc.autoDetach=false");
		expect.add("core.filemode=" + !isWindows);
		expect.add("core.logallrefupdates=true");
		if (isMac)
			expect.add("core.precomposeunicode=true");
		expect.add("core.repositoryformatversion=0");
		if (!FS.DETECTED.supportsSymlinks())
			expect.add("core.symlinks=false");
		assertEquals("expected default configuration",
				Arrays.asList(expect.toArray()).toString(),
				Arrays.asList(output).toString());
