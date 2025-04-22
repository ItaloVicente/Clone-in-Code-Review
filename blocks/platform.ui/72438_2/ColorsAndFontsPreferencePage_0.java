			ColorDefinition colorDef = (ColorDefinition) definition;
			RGB value = colorDef.getValue();
			if (colorDef.getDefaultsTo() == null && (value == null || value == EMPTY_COLOR_VALUE)) {
				return false;
			}
			return colorRegistry.get(definition.getId()) != null;
