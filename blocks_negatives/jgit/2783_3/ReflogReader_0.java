			int p0 = RawParseUtils.next(raw, pos, '\t'); // personident has no
			if (p0 == -1) {
				throw new IllegalArgumentException(
						JGitText.get().rawLogMessageDoesNotParseAsLogEntry);
			}
			int p1 = RawParseUtils.nextLF(raw, p0);
			if (p1 == -1) {
				throw new IllegalArgumentException(
						JGitText.get().rawLogMessageDoesNotParseAsLogEntry);
