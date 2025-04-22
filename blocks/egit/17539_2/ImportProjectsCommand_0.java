				new GitCreateProjectViaWizardWizard(node.getRepository(), path)) {
			@Override
			protected IDialogSettings getDialogBoundsSettings() {
				return Activator.getDefault().getDialogSettings();
			}
		};
