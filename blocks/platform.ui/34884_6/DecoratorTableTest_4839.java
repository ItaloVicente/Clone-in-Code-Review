package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.decorators.DecoratorDefinition;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.tests.navigator.AbstractNavigatorTest;

public abstract class DecoratorEnablementTestCase extends AbstractNavigatorTest
        implements ILabelProviderListener {

    protected DecoratorDefinition definition;

    protected boolean updated = false;

    public DecoratorEnablementTestCase(String testName) {
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
        for (int i = 0; i < definitions.length; i++) {
            if (definitions[i].getId().equals(
                    "org.eclipse.ui.tests.decorators.lightweightdecorator"))
                definition = definitions[i];
        }
    }

    protected DecoratorManager getDecoratorManager() {
        return WorkbenchPlugin.getDefault().getDecoratorManager();
    }

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();
        getDecoratorManager().removeListener(this);
    }


    public void testEnableDecorator()  {
        getDecoratorManager().clearCaches();
        definition.setEnabled(true);
        getDecoratorManager().updateForEnablementChange();

    }

    public void testDisableDecorator() {
        getDecoratorManager().clearCaches();
        definition.setEnabled(false);
        getDecoratorManager().updateForEnablementChange();
    }

    @Override
	public void labelProviderChanged(LabelProviderChangedEvent event) {
        updated = true;
    }

}
