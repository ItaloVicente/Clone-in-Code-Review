		if (baseValue == UNINITIALIZED) {
			EStructuralFeature pd = getPropertyDescriptor();
			if (pd == null) {
				return null;
			}
			baseValue = ValueUtils.getValue(getBean(), pd);
		}
		return baseValue;
	}
