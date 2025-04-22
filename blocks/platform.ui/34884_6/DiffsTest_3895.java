
package org.eclipse.core.tests.databinding.observable;

import org.eclipse.core.databinding.observable.AbstractObservable;
import org.eclipse.core.databinding.observable.DecoratingObservable;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.conformance.util.DisposeEventTracker;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class DecoratingObservableTest extends AbstractDefaultRealmTestCase {
	private IObservable decorated;
	private DecoratingObservable decorator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		decorated = new ObservableStub(Realm.getDefault());
		decorator = new DecoratingObservable(decorated, false);
	}

	public void testDisposeDecorated_DisposesDecorator() {
		DisposeEventTracker tracker = DisposeEventTracker.observe(decorator);
		assertFalse(decorator.isDisposed());
		decorated.dispose();
		assertEquals(1, tracker.count);
		assertTrue(decorator.isDisposed());
	}

	static class ObservableStub extends AbstractObservable {
		public ObservableStub(Realm realm) {
			super(realm);
		}

		@Override
		public boolean isStale() {
			return false;
		}
	}
}
