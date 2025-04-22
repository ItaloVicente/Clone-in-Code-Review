
package org.eclipse.ui.tests.decorators;

import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.decorators.DecoratorDefinition;

public class BadIndexDecoratorTestCase extends DecoratorEnablementTestCase {

	public BadIndexDecoratorTestCase(String testName) {
		super(testName);
	}

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        createTestFile();
        showNav();

        WorkbenchPlugin.getDefault().getDecoratorManager().addListener(this);

        DecoratorDefinition[] definitions = WorkbenchPlugin.getDefault()
                .getDecoratorManager().getAllDecoratorDefinitions();
        for (DecoratorDefinition definition2 : definitions) {
            if (definition2.getId().equals(
                    "org.eclipse.ui.tests.decorators.badIndexDecorator")) {
				definition = definition2;
			}
        }
    }

    public void testNoException() {

        updated = false;
        getDecoratorManager().clearCaches();
        definition.setEnabled(true);
        getDecoratorManager().updateForEnablementChange();
        definition.setEnabled(false);
        getDecoratorManager().updateForEnablementChange();
        updated = false;

    }

}
