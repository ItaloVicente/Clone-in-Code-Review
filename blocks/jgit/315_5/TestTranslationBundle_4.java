
package org.eclipse.jgit.nls;

import java.util.Locale;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;


public class TestNLS extends TestCase {

	public void testNLSLocale() {
		NLS.setLocale(Locale.ROOT);
		GermanTranslatedBundle bundle = GermanTranslatedBundle.get();
		assertEquals(Locale.ROOT

		NLS.setLocale(Locale.GERMAN);
		bundle = GermanTranslatedBundle.get();
		assertEquals(Locale.GERMAN
	}

	public void testJVMDefaultLocale() {
		Locale.setDefault(Locale.ROOT);
		NLS.useJVMDefaultLocale();
		GermanTranslatedBundle bundle = GermanTranslatedBundle.get();
		assertEquals(Locale.ROOT

		Locale.setDefault(Locale.GERMAN);
		NLS.useJVMDefaultLocale();
		bundle = GermanTranslatedBundle.get();
		assertEquals(Locale.GERMAN
	}

	public void testThreadTranslationBundleInheritance() throws InterruptedException {

		class T extends Thread {
			GermanTranslatedBundle bundle;
			@Override
			public void run() {
				bundle = GermanTranslatedBundle.get();
			}
		}

		NLS.setLocale(Locale.ROOT);
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

	public void testParallelThreadsWithDifferentLocales() throws InterruptedException {

		final CyclicBarrier barrier = new CyclicBarrier(2);

		class T extends Thread {
			Locale locale;
			GermanTranslatedBundle bundle;
			Exception e;

			T(Locale locale) {
				this.locale = locale;
			}

			@Override
			public void run() {
				try {
					NLS.setLocale(locale);
					bundle = GermanTranslatedBundle.get();
				} catch (InterruptedException e) {
					this.e = e;
				} catch (BrokenBarrierException e) {
					this.e = e;
				}
			}
		}

		T t1 = new T(Locale.ROOT);
		T t2 = new T(Locale.GERMAN);
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		assertNull("t1 was interrupted or barrier was broken"
		assertNull("t2 was interrupted or barrier was broken"
		assertEquals(Locale.ROOT
		assertEquals(Locale.GERMAN
	}
}
