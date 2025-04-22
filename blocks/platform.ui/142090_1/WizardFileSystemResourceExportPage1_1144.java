		super.createControl(parent);
		giveFocusToDestination();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IDataTransferHelpContextIds.FILE_SYSTEM_EXPORT_WIZARD_PAGE);
	}

	@Override
