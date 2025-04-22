
package org.eclipse.jgit.nls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class NLSTest {

	@Test
	public void testNLSLocale() {
		NLS.setLocale(NLS.ROOT_LOCALE);
		GermanTranslatedBundle bundle = GermanTranslatedBundle.get();
		assertEquals(NLS.ROOT_LOCALE

		NLS.setLocale(Locale.GERMAN);
		bundle = GermanTranslatedBundle.get();
		assertEquals(Locale.GERMAN
	}

	@Test
	public void testJVMDefaultLocale() {
		Locale.setDefault(NLS.ROOT_LOCALE);
		NLS.useJVMDefaultLocale();
		GermanTranslatedBundle bundle = GermanTranslatedBundle.get();
		assertEquals(NLS.ROOT_LOCALE

		Locale.setDefault(Locale.GERMAN);
		NLS.useJVMDefaultLocale();
		bundle = GermanTranslatedBundle.get();
		assertEquals(Locale.GERMAN
	}

	@Test
	public void testThreadTranslationBundleInheritance() throws InterruptedException {

		class T extends Thread {
			GermanTranslatedBundle bundle;
			@Override
			public void run() {
				bundle = GermanTranslatedBundle.get();
			}
		}

		NLS.setLocale(NLS.ROOT_LOCALE);
		GermanTranslatedBundle mainThreadsBundle = GermanTranslatedBundle.get();
		T t = new T();
		t.start();
		t.join();
		assertSame(mainThreadsBundle

		NLS.setLocale(Locale.GERMAN);
		mainThreadsBundle = GermanTranslatedBundle.get();
		t = new T();
		t.start();
		t.join();
		assertSame(mainThreadsBundle
	}

	@Test
	public void testParallelThreadsWithDifferentLocales()
			throws InterruptedException

		final CyclicBarrier barrier = new CyclicBarrier(2);

		class GetBundle implements Callable<TranslationBundle> {

			private Locale locale;

			GetBundle(Locale locale) {
				this.locale = locale;
			}

			@Override
			public TranslationBundle call() throws Exception {
				NLS.setLocale(locale);
				return GermanTranslatedBundle.get();
			}
		}

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
	}
}
