		assertEquals("Page should be completed", true, wizard.page1.canFlipToNextPage());
		assertSame("WizardPage error message should be null", null, wizard.page1.getErrorMessage());

		assertSame("WizardPage.getNexPage() wrong page", wizard.page2, wizard.page1.getNextPage());
		assertSame("Wizard.getNexPage() wrong page", wizard.page2, wizard.getNextPage(wizard.page1));
		assertSame("WizardPage.getPreviousPage() wrong page", wizard.page1, wizard.page2.getPreviousPage());
		assertSame("Wizard.getPreviousPage() wrong page", wizard.page1, wizard.getPreviousPage(wizard.page2));
		assertSame("WizardPage.getNexPage() wrong page", wizard.page3, wizard.page2.getNextPage());
		assertSame("Wizard.getPreviousPage() wrong page", wizard.page2, wizard.getPreviousPage(wizard.page3));

		wizard.page2.textInputField.setText(TheTestWizardPage.BAD_TEXT_FIELD_CONTENTS);
		assertEquals("Wizard should not be able to finish", false, wizard.canFinish());
		wizard.page2.textInputField.setText(TheTestWizardPage.GOOD_TEXT_FIELD_CONTENTS);
		assertEquals("Wizard should be able to finish", true, wizard.canFinish());
