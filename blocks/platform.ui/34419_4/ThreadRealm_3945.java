
package org.eclipse.core.tests.databinding.observable;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;

public class RealmTest extends TestCase {
	public void testSetDefaultWithRunnable() throws Exception {
		Realm oldRealm = new CurrentRealm(true);
		final Realm newRealm = new CurrentRealm(true);

		RealmTester.setDefault(oldRealm);
		Realm.runWithDefault(newRealm, new Runnable() {
			@Override
			public void run() {
				assertEquals("new realm should be default", newRealm, Realm.getDefault());
			}
		});

		assertEquals("old realm should have been restored", oldRealm, Realm.getDefault());
	}
}
