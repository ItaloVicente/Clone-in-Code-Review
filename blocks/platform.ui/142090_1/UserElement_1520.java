		return new Object[0];
	}

	private Boolean getCoop() {
		if (coop == null)
			coop = Boolean.FALSE;
		return coop;
	}

	static  ArrayList<PropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	@Override
