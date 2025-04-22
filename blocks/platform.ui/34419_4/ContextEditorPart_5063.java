
package org.eclipse.ui.tests.leaks;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISourceProviderListener;
import org.junit.Assert;
import org.junit.Test;

public class Bug397302Tests {

	private static class TestListener implements ISourceProviderListener {

		private long callCount = 0;

		public long getCallCount() {
			return callCount;
		}

		public TestListener() {
		}

		@Override
		public void sourceChanged(int sourcePriority, String sourceName,
				Object sourceValue) {
			++callCount;
		}

		@Override
		public void sourceChanged(int sourcePriority, Map sourceValuesByName) {
			++callCount;
			}
	}

	private static final class TestSourceProvider extends AbstractSourceProvider {

		@Override
		public void dispose() {
		}

		@Override
		public Map getCurrentState() {
			return Collections.EMPTY_MAP;
		}

		@Override
		public String[] getProvidedSourceNames() {
			return new String[] {};
		}

		public void callOut() {
			this.fireSourceChanged(0, Collections.EMPTY_MAP);
		}

	}

	@Test
	public void testBugAsDescribed() {
		final TestSourceProvider testSourceProvider = new TestSourceProvider();
		TestListener a = new TestListener();
		TestListener b = new TestListener();
		final WeakReference<TestListener> listenerARef = new WeakReference<TestListener>(a);
		final WeakReference<TestListener> listenerBRef = new WeakReference<TestListener>(b);

		testSourceProvider.addSourceProviderListener(a);
		testSourceProvider.addSourceProviderListener(b);

		testSourceProvider.callOut();

		Assert.assertEquals(1, a.getCallCount());
		Assert.assertEquals(1, b.getCallCount());

		testSourceProvider.removeSourceProviderListener(a);
		testSourceProvider.removeSourceProviderListener(b);

		testSourceProvider.callOut();

		Assert.assertEquals(1, a.getCallCount());
		Assert.assertEquals(1, b.getCallCount());

		a = null;
		b = null;

		System.gc();

		Assert.assertNull("Reference A", listenerARef.get());
		Assert.assertNull("Reference B", listenerBRef.get());

		testSourceProvider.callOut();

	}

	@Test
	public void testRemoveDuringCallOut() {
		final TestSourceProvider testSourceProvider = new TestSourceProvider();
		final TestListener testListener = new TestListener() {
			@Override
			public void sourceChanged(int sourcePriority, Map sourceValuesByName) {
				testSourceProvider.removeSourceProviderListener(this);
			}

		};
		testSourceProvider.addSourceProviderListener(testListener);

		testSourceProvider.callOut();

	}
}
