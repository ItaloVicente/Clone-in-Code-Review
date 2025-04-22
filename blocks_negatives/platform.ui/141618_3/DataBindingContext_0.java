		ValidationStatusProvider[] statusProviderArray = validationStatusProviders
				.toArray(new ValidationStatusProvider[validationStatusProviders
						.size()]);
		for (int i = 0; i < statusProviderArray.length; i++) {
			if (!statusProviderArray[i].isDisposed())
				statusProviderArray[i].dispose();
