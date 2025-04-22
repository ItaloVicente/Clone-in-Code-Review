
package org.eclipse.jface.tests.databinding;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.widgets.Display;

public class AbstractDefaultRealmTestCase extends TestCase {
	private Realm previousRealm;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		previousRealm = Realm.getDefault();

		Display display = Display.getCurrent() != null
				&& !Display.getCurrent().isDisposed() ? Display.getCurrent()
				: Display.getDefault();
		RealmTester.setDefault(DisplayRealm.getRealm(display));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		RealmTester.setDefault(previousRealm);
	}
}
