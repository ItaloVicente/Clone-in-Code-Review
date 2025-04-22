        IWorkbenchCommandSupport commandSupport = window.getWorkbench()
                .getCommandSupport();
        ICommandManager commandManager = commandSupport.getCommandManager();
        KeySequence expectedKeyBinding = KeySequence
        String commandId = commandManager.getPerfectMatch(expectedKeyBinding);
        assertEquals("org.eclipse.ui.tests.TestCommandId", commandId); //$NON-NLS-1$
    }
