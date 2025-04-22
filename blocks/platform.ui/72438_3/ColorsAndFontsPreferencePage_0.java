			ColorDefinition colorDef = (ColorDefinition) definition;
			RGB value = colorDef.getValue();
			if ((value == null || value == EMPTY_COLOR_VALUE) && colorDef.getDefaultsTo() == null) {
				return false;
			}
			return colorRegistry.get(definition.getId()) != null;
