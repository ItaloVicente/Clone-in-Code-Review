		if (useOldRule)
			System.err
					.println("IgnoreRule can't understand wildmatch rules, skipping testWildmatch!");

		Boolean assume = useOldRule;
		assertMatched("**/a/b", "a/b", assume);
		assertMatched("**/a/b", "c/a/b", assume);
		assertMatched("**/a/b", "c/d/a/b", assume);
		assertMatched("**/**/a/b", "c/d/a/b", assume);

		assertMatched("/**/a/b", "a/b", assume);
		assertMatched("/**/a/b", "c/a/b", assume);
		assertMatched("/**/a/b", "c/d/a/b", assume);
		assertMatched("/**/**/a/b", "c/d/a/b", assume);

		assertMatched("a/b/**", "a/b", assume);
		assertMatched("a/b/**", "a/b/c", assume);
		assertMatched("a/b/**", "a/b/c/d/", assume);
		assertMatched("a/b/**/**", "a/b/c/d", assume);

		assertMatched("**/a/**/b", "c/d/a/b", assume);
		assertMatched("**/a/**/b", "c/d/a/e/b", assume);
		assertMatched("**/**/a/**/**/b", "c/d/a/e/b", assume);

		assertMatched("/**/a/**/b", "c/d/a/b", assume);
		assertMatched("/**/a/**/b", "c/d/a/e/b", assume);
		assertMatched("/**/**/a/**/**/b", "c/d/a/e/b", assume);

		assertMatched("a/**/b", "a/b", assume);
		assertMatched("a/**/b", "a/c/b", assume);
		assertMatched("a/**/b", "a/c/d/b", assume);
		assertMatched("a/**/**/b", "a/c/d/b", assume);

		assertMatched("a/**/b/**/c", "a/c/b/d/c", assume);
		assertMatched("a/**/**/b/**/**/c", "a/c/b/d/c", assume);
