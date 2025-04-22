		return Util.equals(naturalKey, castedObject.naturalKey);
	}

	public String format() {
		return KeyFormatterFactory.getDefault().format(this);
	}

	public Set getModifierKeys() {
		return Collections.unmodifiableSet(modifierKeys);
	}

	public NaturalKey getNaturalKey() {
		return naturalKey;
	}

	@Override
