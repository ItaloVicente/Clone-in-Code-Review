		assertNormalized("Bug 12345::::Hello World"
		assertNormalized("Bug 12345 :::: Hello World"
		assertNormalized("Bug 12345 :::: Hello::: World"
				"Bug_12345_Hello-World");
		assertNormalized(":::Bug 12345 - Hello World"
		assertNormalized("---Bug 12345 - Hello World"
		assertNormalized("Bug 12345 ---- Hello --- World"
				"Bug_12345_Hello_World");
		assertNormalized("Bug 12345 - Hello World!"
		assertNormalized("Bug 12345 : Hello World!"
		assertNormalized("Bug 12345 _ Hello World!"
		assertNormalized("Bug 12345   -       Hello World!"
				"Bug_12345_Hello_World!");
		assertNormalized(" Bug 12345   -   Hello World! "
				"Bug_12345_Hello_World!");
		assertNormalized(" Bug 12345   -   Hello World!   "
				"Bug_12345_Hello_World!");
		assertNormalized("Bug 12345   -   Hello______ World!"
				"Bug_12345_Hello_World!");
		assertNormalized("_Bug 12345 - Hello World!"
	}
