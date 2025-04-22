		T t1 = new T(NLS.ROOT_LOCALE);
		T t2 = new T(Locale.GERMAN);
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		assertNull("t1 was interrupted or barrier was broken", t1.e);
		assertNull("t2 was interrupted or barrier was broken", t2.e);
		assertEquals(NLS.ROOT_LOCALE, t1.bundle.effectiveLocale());
		assertEquals(Locale.GERMAN, t2.bundle.effectiveLocale());
