					styledString.append('[', StyledString.DECORATIONS_STYLER);
					styledString.append(host, StyledString.DECORATIONS_STYLER);
					styledString.append(']', StyledString.DECORATIONS_STYLER);
				} catch (IllegalArgumentException e) {
					styledString.append(e.getMessage());
					Activator.logError(e.getMessage(), e);
