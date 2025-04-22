		return ((ObjectContribution) currentContribution).isApplicableTo(object);
	}

	private void readConfigElement() {
		currentContribution = createContribution();
		readElementChildren(config);
		configRead = true;
	}

	@Override
