		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			Future<TranslationBundle> root = pool.submit(new GetBundle(
					NLS.ROOT_LOCALE));
			Future<TranslationBundle> german = pool.submit(new GetBundle(
					Locale.GERMAN));
			assertEquals(NLS.ROOT_LOCALE
			assertEquals(Locale.GERMAN
		} finally {
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE
		}
