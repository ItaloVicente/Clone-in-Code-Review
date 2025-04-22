package org.eclipse.jface.suites;

import org.eclipse.jface.widgets.TestUnitButtonFactory;
import org.eclipse.jface.widgets.TestUnitCompositeFactory;
import org.eclipse.jface.widgets.TestUnitControlFactory;
import org.eclipse.jface.widgets.TestUnitItemFactory;
import org.eclipse.jface.widgets.TestUnitLabelFactory;
import org.eclipse.jface.widgets.TestUnitSpinnerFactory;
import org.eclipse.jface.widgets.TestUnitTableColumnFactory;
import org.eclipse.jface.widgets.TestUnitTableFactory;
import org.eclipse.jface.widgets.TestUnitTextFactory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //
		TestUnitControlFactory.class, //
		TestUnitButtonFactory.class, //
		TestUnitLabelFactory.class, //
		TestUnitCompositeFactory.class, //
		TestUnitSpinnerFactory.class, //
		TestUnitTextFactory.class, //
		TestUnitTableFactory.class, //
		TestUnitItemFactory.class, //
		TestUnitTableColumnFactory.class, //
})
public class AllUnitTests {

}
