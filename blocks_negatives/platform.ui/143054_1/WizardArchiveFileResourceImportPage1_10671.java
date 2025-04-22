        super.createControl(parent);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
                IDataTransferHelpContextIds.ZIP_FILE_IMPORT_WIZARD_PAGE);
    }

    /**
     *	Create the options specification widgets. There is only one
     * in this case so create no group.
     *
     *	@param parent org.eclipse.swt.widgets.Composite
     */
    @Override
