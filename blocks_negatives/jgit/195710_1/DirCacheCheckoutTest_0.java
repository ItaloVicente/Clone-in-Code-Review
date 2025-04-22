		char[] bytes = new char[253];
		Arrays.fill(bytes, 'f');
		String longFileName = new String(bytes);
		doit(mkmap(longFileName, "a"), mkmap(longFileName, "b"),
				mkmap(longFileName, "a"));
		writeTrashFile(longFileName, "a");
