	}

	private void finishPrimary() {
		if (deferPrimary != null) {
			ArrayList primary = new ArrayList();
			for (Iterator i = deferPrimary.iterator(); i.hasNext();) {
				String id = (String) i.next();
				WorkbenchWizardElement element = wizardElements == null ? null : wizardElements.findWizard(id, true);
				if (element != null) {
					primary.add(element);
				}
			}
