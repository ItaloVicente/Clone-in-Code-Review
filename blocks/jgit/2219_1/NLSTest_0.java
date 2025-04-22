		ExecutorService pool = Executors.newFixedThreadPool(2);
		Future<TranslationBundle> root = pool.submit(new GetBundle(
				NLS.ROOT_LOCALE));
		Future<TranslationBundle> german = pool.submit(new GetBundle(
				Locale.GERMAN));
		assertEquals(NLS.ROOT_LOCALE
		assertEquals(Locale.GERMAN
