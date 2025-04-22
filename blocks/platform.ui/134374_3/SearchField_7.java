		txtQuickAccess.addKeyListener(new TriggerSequenceKeyAdapter(QUICK_ACCESS_SWITCH_TO_DIALOG_COMMAND_ID, () -> {
			activated = false;
			QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
					eCommandService.createCommand(QUICK_ACCESS_COMMAND_ID, null).getCommand(),
					txtQuickAccess.getText());
			dialog.open();
		}, bindingService));
