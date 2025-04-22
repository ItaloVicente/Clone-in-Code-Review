        if (page != null) {
            page.setWizard(wizard);
            dialog.showPage(page);
        }
        return dialog;
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
