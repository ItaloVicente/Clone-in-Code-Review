			String filter = filterText.getText();
			if (filter.startsWith("*") || filter.startsWith("?") || filter.startsWith("?")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				searchPattern.setPattern(filter);
			} else {
				searchPattern.setPattern("*" + filter); //$NON-NLS-1$
			}
