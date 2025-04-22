	private static void checkValidPathSegment(ObjectChecker chk
			CanonicalTreeParser t) throws InvalidPathException {
		try {
			int ptr = t.getNameOffset();
			int end = ptr + t.getNameLength();
			chk.checkPathSegment(t.getEntryPathBuffer()
		} catch (CorruptObjectException err) {
			String path = t.getEntryPathString();
			InvalidPathException i = new InvalidPathException(path);
			i.initCause(err);
			throw i;
		}
