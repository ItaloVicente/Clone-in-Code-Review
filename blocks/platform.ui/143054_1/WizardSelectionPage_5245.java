		super.dispose();
		for (int i = 0; i < selectedWizardNodes.size(); i++) {
			selectedWizardNodes.get(i).dispose();
		}
	}

	@Override
