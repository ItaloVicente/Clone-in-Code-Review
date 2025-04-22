	protected void remove(FontDefinition definition) {
		if (findFont(definition.getId()) == null) {
			return;
		}
		fonts.remove(definition);
	}

