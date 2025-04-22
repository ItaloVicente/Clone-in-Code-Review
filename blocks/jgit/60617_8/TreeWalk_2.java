	public AttributesNodeProvider getAttributesNodeProvider() {
		return attributesNodeProvider;
	}

	public Attributes getAttributes() {
		if (attrs != null)
			return attrs;

		if (attributesNodeProvider == null) {
			throw new IllegalStateException(
		}

		try {
			if (macroExpander == null) {
				macroExpander = new MacroExpander(this);
			}
			attrs = macroExpander.getAttributes();
			return attrs;
		} catch (IOException e) {
			throw new JGitInternalException("Error while parsing attributes"
					e);
		}
	}

