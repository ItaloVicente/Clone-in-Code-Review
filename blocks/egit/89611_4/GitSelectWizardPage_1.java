	private String getFilterCountString() {
		GitCreateProjectViaWizardWizard wizard = (GitCreateProjectViaWizardWizard) getWizard();
		List<String> filter = wizard.getFilter();
		if (filter.size() > 1) {
			return MessageFormat.format(UIText.GitSelectWizardPage_Selected,
					new Integer(filter.size()));
		}
		return ""; //$NON-NLS-1$
	}

