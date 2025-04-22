			ColorDefinition colorDef = (ColorDefinition) definition;
			if (colorDef.getDefaultsTo() == null
					&& (colorDef.getValue() == null || colorDef.getValue() == EMPTY_COLOR_VALUE)) {
				return false;
			}

			return colorRegistry.get(definition.getId()) != null;
