package org.eclipse.ui.tests.decorators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.decorators.DecoratorDefinition;
import org.eclipse.ui.internal.decorators.DecoratorManager;

public class ExceptionDecoratorTestCase extends DecoratorEnablementTestCase
        implements ILabelProviderListener {
    private Collection problemDecorators = new ArrayList();

    private DecoratorDefinition light;

    public ExceptionDecoratorTestCase(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        HeavyNullImageDecorator.fail = true;
        HeavyNullTextDecorator.fail = true;
        NullImageDecorator.fail = true;
        DecoratorDefinition[] definitions = WorkbenchPlugin.getDefault()
                .getDecoratorManager().getAllDecoratorDefinitions();
        for (int i = 0; i < definitions.length; i++) {
            String id = definitions[i].getId();
            if (id.equals("org.eclipse.ui.tests.heavyNullImageDecorator")
                    || id.equals("org.eclipse.ui.tests.heavyNullTextDecorator")) {
                definitions[i].setEnabled(true);
                problemDecorators.add(definitions[i]);
            }

            if (id.equals("org.eclipse.ui.tests.lightNullImageDecorator")) {
                definitions[i].setEnabled(true);
                light = definitions[i];
            }
        }
        super.doSetUp();
	}

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();

        try {
            Platform.getJobManager().join(DecoratorManager.FAMILY_DECORATE,
                    null);
        } catch (OperationCanceledException e) {
        } catch (InterruptedException e) {
        }

        Iterator problemIterator = problemDecorators.iterator();
        while (problemIterator.hasNext()) {
            DecoratorDefinition next = (DecoratorDefinition) problemIterator
                    .next();
            assertFalse("Enabled " + next.getName(), next.isEnabled());
        }

        light.setEnabled(false);
    }
}
