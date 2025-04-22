		for (int i = 0; i < pages.size(); i++) {
			IWizardPage page = pages.get(i);
			page.createControl(pageContainer);
			Assert.isNotNull(
					page.getControl(),
					"getControl of wizard page returns null. Did you call setControl() in your wizard page?"); //$NON-NLS-1$
		}
	}

	@Override
