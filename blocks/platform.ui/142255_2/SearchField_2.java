		providers.addAll(QuickAccessExtensionManager.getProviders(() -> {
			txtQuickAccess.getDisplay().asyncExec(() -> {
				txtQuickAccess.setText(lastSelectionFilter);
				txtQuickAccess.setFocus();
				SearchField.this.showList();
			});
		}));
