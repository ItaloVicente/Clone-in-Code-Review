				return getStyledText(element).getString();
			}

			@Override
			public StyledString getStyledText(Object element) {
				StyledString result = new StyledString();
				DecorationResult decoration = getDecoration(element);
				String extra = decoration.getPrefix();
				if (extra != null) {
					result.append(extra, StyledString.DECORATIONS_STYLER);
				}
				result.append(((PreviewResource) element).getName());
				extra = decoration.getSuffix();
				if (extra != null) {
					result.append(extra, StyledString.DECORATIONS_STYLER);
				}
				return result;
