		if (useOldRule)
			System.err
					.println("IgnoreRule can't understand wildmatch rules, skipping testWildmatchDoNotMatch!");

		Boolean assume = useOldRule;
		assertNotMatched("**/a/b", "a/c/b", assume);
		assertNotMatched("!/**/*.zip", "c/a/b.zip", assume);
		assertNotMatched("!**/*.zip", "c/a/b.zip", assume);
		assertNotMatched("a/**/b", "a/c/bb", assume);
