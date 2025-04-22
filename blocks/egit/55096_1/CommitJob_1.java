		if (pushTo == PushMode.GERRIT) {
			final Wizard pushWizard = new PushToGerritWizard(repository);
			openPushWizard(pushWizard);
		}
		else {
		if (config == null) {
				try {
					Wizard pushWizard = null;
