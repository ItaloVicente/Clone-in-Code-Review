					label.append(parsed.lastSegment());
					if (suffix != null) {
						label.append(suffix);
					}
					label.append(" - ") //$NON-NLS-1$
							.append(parsed.removeLastSegments(1).toString());
					return label.toString();
