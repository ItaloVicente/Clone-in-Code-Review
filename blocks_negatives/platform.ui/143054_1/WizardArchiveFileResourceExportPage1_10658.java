        super.createControl(parent);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
                IDataTransferHelpContextIds.ZIP_FILE_EXPORT_WIZARD_PAGE);
    }

    /**
     *	Create the export options specification widgets.
     *
     */
    @Override
