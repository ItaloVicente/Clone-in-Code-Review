		if (index != WHOLE_COLLECTION && index >= getLength()) {
			createPath(context);
		}
		setValue(value);
		return this;
	}

	@Override
