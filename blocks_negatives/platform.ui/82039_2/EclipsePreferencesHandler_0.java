	@Override
	public String retrieveCSSProperty(Object element, String property,
			String pseudo, CSSEngine engine) throws Exception {
		return null;
	}

	protected void overrideProperty(IEclipsePreferences preferences,
			CSSValue value) {
		Matcher matcher = PROPERTY_NAME_AND_VALUE_PATTERN.matcher(value
				.getCssText());
