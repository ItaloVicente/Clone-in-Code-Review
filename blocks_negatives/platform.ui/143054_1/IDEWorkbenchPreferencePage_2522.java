    	PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IWorkbenchHelpContextIds.WORKBENCH_PREFERENCE_PAGE);

        Composite composite = createComposite(parent);

        createSettings(composite);
        createOpenModeGroup(composite);
