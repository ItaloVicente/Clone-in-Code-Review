				if (getPreferenceStore().isDefault(createPreferenceKey(definition)))
					return true;
			} else {
				FontData[] ancestor = getFontAncestorValue(definition);
				if (ancestor == null)
					return true;
				if (Arrays.equals(getFontValue(definition), ancestor))
					return true;
			}
