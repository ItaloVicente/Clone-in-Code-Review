
package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.BasicWorkingSetElementAdapter;
import org.eclipse.ui.tests.menus.ObjectContributionClasses;

public class IWorkingSetElementAdapterTests extends TestCase {

	String data = "org.eclipse.ui.tests.menus.ObjectContributionClasses$ICommon;adapt=true,org.eclipse.ui.tests.menus.ObjectContributionClasses$IF;adapt=true";
	BasicWorkingSetElementAdapter adapter;

	@Override
	protected void setUp() throws Exception {
		adapter = new BasicWorkingSetElementAdapter();
		adapter.setInitializationData(null, "class", data);
	}

	@Override
	protected void tearDown() throws Exception {
		adapter.dispose();
	}

	public void testBasicWorkingSetElementAdapter_Direct() {
		IAdaptable [] result = adapter.adaptElements(null, new IAdaptable[] {new ObjectContributionClasses.Common()});
		assertEquals(1, result.length);
		assertEquals(ObjectContributionClasses.Common.class, result[0].getClass());
	}

	public void testBasicWorkingSetElementAdapter_Inheritance() {
		IAdaptable [] result = adapter.adaptElements(null, new IAdaptable[] {new ObjectContributionClasses.D()});
		assertEquals(1, result.length);
		assertEquals(ObjectContributionClasses.D.class, result[0].getClass());
	}

	public void testBasicWorkingSetElementAdapter_IAdaptable() {
		IAdaptable[] result = adapter.adaptElements(null,
				new IAdaptable[] { new ObjectContributionClasses.E()  });
		assertEquals(1, result.length);
		assertEquals(ObjectContributionClasses.F.class, result[0].getClass());
	}

	public void testBasicWorkingSetElementAdapter_AdapterManager() {
		IAdaptable[] result = adapter.adaptElements(null,
				new IAdaptable[] { new ObjectContributionClasses.E1()  });
		assertEquals(1, result.length);
		assertEquals(ObjectContributionClasses.Common.class, result[0].getClass());
	}
}
