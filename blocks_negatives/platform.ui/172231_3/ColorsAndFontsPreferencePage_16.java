				if (getPreferenceStore().isDefault(createPreferenceKey(definition)))
					return true;
			} else {
				RGB rgb = getColorValue(definition);
				if (rgb != null && rgb.equals(getColorAncestorValue(definition)))
					return true;
			}
