		for (int i = AutoCRLFOutputStream.BUFFER_SIZE - 10; i < AutoCRLFOutputStream.BUFFER_SIZE + 10; i++) {
			String s1 = repeat("a"
			assertNoCrLf(s1
			String s2 = repeat("\0"
			assertNoCrLf(s2
		}
