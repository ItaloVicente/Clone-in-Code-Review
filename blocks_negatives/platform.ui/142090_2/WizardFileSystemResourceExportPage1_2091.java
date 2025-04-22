        super.createControl(parent);
        giveFocusToDestination();
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
                IDataTransferHelpContextIds.FILE_SYSTEM_EXPORT_WIZARD_PAGE);
    }

    /**
     *	Create the export destination specification widgets
     *
     *	@param parent org.eclipse.swt.widgets.Composite
     */
    @Override
