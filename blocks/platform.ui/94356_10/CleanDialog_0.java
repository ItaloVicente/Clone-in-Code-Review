			if (!filterText.getText().equals("*")) { //$NON-NLS-1$
				filterRegexPattern.setPattern(filterText.getText());
			}

			if (filterText.getText().isEmpty()) {
				filterText.setMessage(IDEWorkbenchMessages.CleanDialog_typeFilterText);
			}

