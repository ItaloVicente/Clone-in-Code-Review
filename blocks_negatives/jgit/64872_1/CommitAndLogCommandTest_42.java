		Git git = new Git(db);
		String messageHeader = "Some header line\n\nSome detail explanation\n";
		String changeIdTemplate = "\nChange-Id: I"
				+ ObjectId.zeroId().getName() + "\n";
		String messageFooter = "Some foooter lines\nAnother footer line\n";
		RevCommit commit = git.commit().setMessage(
				messageHeader + messageFooter)
				.setInsertChangeId(true).call();
		byte[] chars = commit.getFullMessage().getBytes();
		int lastLineBegin = RawParseUtils.prevLF(chars, chars.length - 2);
		String lastLine = RawParseUtils.decode(chars, lastLineBegin + 1,
				chars.length);
		assertTrue(lastLine.contains("Change-Id:"));
		assertFalse(lastLine.contains(
				"Change-Id: I" + ObjectId.zeroId().getName()));

		commit = git.commit().setMessage(
				messageHeader + changeIdTemplate + messageFooter)
				.setInsertChangeId(true).call();
		chars = commit.getFullMessage().getBytes();
		int lineStart = 0;
		int lineEnd = 0;
		for (int i = 0; i < 4; i++) {
			lineStart = RawParseUtils.nextLF(chars, lineStart);
		}
		lineEnd = RawParseUtils.nextLF(chars, lineStart);

		String line = RawParseUtils.decode(chars, lineStart, lineEnd);

		assertTrue(line.contains("Change-Id:"));
		assertFalse(line.contains(
				"Change-Id: I" + ObjectId.zeroId().getName()));

		commit = git.commit().setMessage(
				messageHeader + changeIdTemplate + messageFooter)
				.setInsertChangeId(false).call();
		chars = commit.getFullMessage().getBytes();
		lineStart = 0;
		lineEnd = 0;
		for (int i = 0; i < 4; i++) {
			lineStart = RawParseUtils.nextLF(chars, lineStart);
