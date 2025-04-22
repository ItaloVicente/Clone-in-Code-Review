	static enum TestEnum {
		ONE_TWO;
	}

	public void testGetEnum() throws ConfigInvalidException {
		Config c = parse("[s]\na = ON\nb = input\nc = true\nd = off\n");
		assertSame(CoreConfig.AutoCRLF.TRUE
				CoreConfig.AutoCRLF.FALSE));

		assertSame(CoreConfig.AutoCRLF.INPUT
				CoreConfig.AutoCRLF.FALSE));

		assertSame(CoreConfig.AutoCRLF.TRUE
				CoreConfig.AutoCRLF.FALSE));

		assertSame(CoreConfig.AutoCRLF.FALSE
				CoreConfig.AutoCRLF.TRUE));

		c = new Config();
		assertSame(CoreConfig.AutoCRLF.FALSE
				CoreConfig.AutoCRLF.FALSE));

		c = parse("[s \"b\"]\n\tc = one two\n");
		assertSame(TestEnum.ONE_TWO
	}

	public void testSetEnum() {
		final Config c = new Config();
		c.setEnum("s"
		assertEquals("[s \"b\"]\n\tc = one two\n"
	}

