		assertArrayEquals("expected default configuration", //
				new String[] { "core.autocrlf=false", //
						"core.filemode=true", //
						"core.logallrefupdates=true", //
						"core.repositoryformatversion=0", //
						"" /* ends with LF (last line empty) */}, output);
