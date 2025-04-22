	public DecoratingLabelProvider(ILabelProvider provider,
			ILabelDecorator decorator) {
		Assert.isNotNull(provider);
		this.provider = provider;
		this.decorator = decorator;
	}

	@Override
