        final IContext context1 = contextManager
                .getContext("org.eclipse.ui.tests.contexts.context1");
        assertTrue(
                "Context contributed via 'org.eclipse.ui.contexts' is not loaded properly.",
                context1.isDefined());
        assertEquals(
                "Context contributed via 'org.eclipse.ui.contexts' does not get its name.",
                "Test Context 1", context1.getName());
