			String filter = filterText.getText();
			if (filter.startsWith("*") || filter.startsWith("?") || filter.startsWith("?")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				searchPattern.setPattern(filterText.getText());
			} else {
				searchPattern.setPattern("*" + filterText.getText()); //$NON-NLS-1$
			}

			if (filterText.getText().isEmpty()) {
				filterText.setMessage(IDEWorkbenchMessages.CleanDialog_typeFilterText);
			}

