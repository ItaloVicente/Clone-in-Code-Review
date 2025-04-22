		IBindingService bindingService = PlatformUI
				.getWorkbench().getService(IBindingService.class);
		dec
				.setDescription(NLS
						.bind(
								WorkbenchMessages.ContentAssist_Cue_Description_Key,
								bindingService
										.getBestActiveBindingFormattedFor(getCommandId())));
