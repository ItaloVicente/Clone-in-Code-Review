        for (int i = 0; i < pages.size(); i++) {
            IWizardPage page = pages.get(i);
            page.createControl(pageContainer);
            Assert.isNotNull(page.getControl());
        }
    }

    /**
     * The <code>Wizard</code> implementation of this <code>IWizard</code>
     * method disposes all the pages controls using
     * <code>DialogPage.dispose</code>. Subclasses should extend this method
     * if the wizard instance maintains addition SWT resource that need to be
     * disposed.
     */
    @Override
