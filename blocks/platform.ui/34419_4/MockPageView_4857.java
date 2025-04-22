package org.eclipse.ui.tests.contexts;

import org.eclipse.ui.contexts.IContext;
import org.eclipse.ui.contexts.IContextManager;
import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.contexts.NotDefinedException;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class ExtensionTestCase extends UITestCase {

    public ExtensionTestCase(final String testName) {
        super(testName);
    }

    public final void testAcceleratorScopes() throws NotDefinedException {
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContextManager contextManager = contextSupport
                .getContextManager();

        final IContext context1 = contextManager
                .getContext("org.eclipse.ui.tests.acceleratorScopes.test1");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.acceleratorScopes' is not loaded properly.",
                context1.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.acceleratorScopes' does not get its name.",
                "Test Accelerator Scope 1", context1.getName());

        final IContext context2 = contextManager
                .getContext("org.eclipse.ui.tests.acceleratorScopes.test2");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.acceleratorScopes' is not loaded properly.",
                context2.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.acceleratorScopes' does not get its name.",
                "Test Accelerator Scope 2", context2.getName());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.acceleratorScopes' does not get its parent.",
                "org.eclipse.ui.tests.acceleratorScopes.test1", context2
                        .getParentId());
    }

    public final void testCommandsScopes() throws NotDefinedException {
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContextManager contextManager = contextSupport
                .getContextManager();

        final IContext context1 = contextManager
                .getContext("org.eclipse.ui.tests.commands.scope1");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.commands' is not loaded properly.",
                context1.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.commands' does not get its name.",
                "Test Scope 1", context1.getName());

        final IContext context2 = contextManager
                .getContext("org.eclipse.ui.tests.commands.scope2");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.commands' is not loaded properly.",
                context2.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.commands' does not get its name.",
                "Test Scope 2", context2.getName());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.commands' does not get its parent.",
                "org.eclipse.ui.tests.commands.scope1", context2.getParentId());
    }

    public final void testContexts() throws NotDefinedException {
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContextManager contextManager = contextSupport
                .getContextManager();

        final IContext context1 = contextManager
                .getContext("org.eclipse.ui.tests.contexts.context1");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.contexts' is not loaded properly.",
                context1.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.contexts' does not get its name.",
                "Test Context 1", context1.getName());

        final IContext context2 = contextManager
                .getContext("org.eclipse.ui.tests.contexts.context2");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.contexts' is not loaded properly.",
                context2.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.contexts' does not get its name.",
                "Test Context 2", context2.getName());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.contexts' does not get its parent.",
                "org.eclipse.ui.tests.contexts.context1", context2
                        .getParentId());
    }
}
