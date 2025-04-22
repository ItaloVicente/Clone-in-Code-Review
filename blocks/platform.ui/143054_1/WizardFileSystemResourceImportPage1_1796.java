		super.createControl(parent);
		validateSourceGroup();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IDataTransferHelpContextIds.FILE_SYSTEM_IMPORT_WIZARD_PAGE);
	}

	@Override
