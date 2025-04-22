
package org.eclipse.jface.tests.databinding;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;

public class AbstractJUnit4RealmTestCase {
	private Realm previousRealm;

	@Before
	public void setUp() throws Exception {

		previousRealm = Realm.getDefault();

		Display display = Display.getCurrent() != null
				&& !Display.getCurrent().isDisposed() ? Display.getCurrent()
				: Display.getDefault();
		RealmTester.setDefault(DisplayRealm.getRealm(display));
	}

	protected void runAsync() {
		Display display = Display.getCurrent();

		while (display.readAndDispatch()) {
		}
	}

	@After
	public void tearDown() throws Exception {
		RealmTester.setDefault(previousRealm);
	}
}
