		assertMatch("\\out", "\\out", true);
		assertMatch("\\out", "a/\\out", true);
		assertMatch("c:\\/", "c:\\/", true);
		assertMatch("c:\\/", "a/c:\\/", true);
		assertMatch("c:\\tmp", "c:\\tmp", true);
		assertMatch("c:\\tmp", "a/c:\\tmp", true);
