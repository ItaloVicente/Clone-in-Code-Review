		lineEnd = RawParseUtils.nextLF(chars, lineStart);

		line = RawParseUtils.decode(chars, lineStart, lineEnd);

		assertTrue(commit.getFullMessage().contains(
				"Change-Id: I" + ObjectId.zeroId().getName()));
