	private void decorateWithFilterCount(Button button) {
		if (getWizard() instanceof GitCreateProjectViaWizardWizard) {
			GitCreateProjectViaWizardWizard wizard = (GitCreateProjectViaWizardWizard) getWizard();
			List<String> filter = wizard.getFilter();
			if (filter.size() > 1) {
				String decoration = MessageFormat.format(
						UIText.GitSelectWizardPage_Selected,
						new Integer(filter.size()));
				button.setText(button.getText() + " " + decoration); //$NON-NLS-1$
			}
		}
	}

