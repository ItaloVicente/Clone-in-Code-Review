		if (!confirmationRequired) {
			confirmationPage.setPageComplete(true);
			IWizardContainer container = getContainer();
			if (container instanceof IPageChangeProvider) {
				((IPageChangeProvider) container)
						.addPageChangedListener(event -> {
							if (event.getSelectedPage() == confirmationPage) {
								confirmationRequired = true;
							}
						});
			}
		}
