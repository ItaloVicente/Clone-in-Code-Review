				final DecorationResult decoration = getDecoration(element);
				final StringBuilder buffer = new StringBuilder();
				final String prefix = decoration.getPrefix();
				if (prefix != null)
					buffer.append(prefix);
				buffer.append(((PreviewResource) element).getName());
				final String suffix = decoration.getSuffix();
				if (suffix != null)
					buffer.append(suffix);
				return buffer.toString();
