			providers.addAll(QuickAccessExtensionManager.getProviders(() -> {
				if (display != null) {
					display.asyncExec(() -> {
						QuickAccessDialog dialog = new QuickAccessDialog(window, invokingCommand);
						dialog.filterText.setText(lastSelectionFilter);
						dialog.open();
					});
				}
			}));
