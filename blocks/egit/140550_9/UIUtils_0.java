		return () -> {
			String value = textField.getText();
			if (value.length() > 0) {
				if (value.length() > 2000) {
					value = value.substring(0, 1999);
				}
				IDialogSettings settings = org.eclipse.egit.ui.Activator
						.getDefault().getDialogSettings();
				String[] existingValues = settings.getArray(preferenceKey);
				if (existingValues == null) {
					existingValues = new String[] { value };
					settings.put(preferenceKey, existingValues);
				} else {

					List<String> values = new ArrayList<>(
							existingValues.length + 1);

					for (String existingValue : existingValues)
						values.add(existingValue);
					if (values.indexOf(value) == 0)
						return;

					values.remove(value);
					values.add(0, value);
					while (values.size() > 10)
						values.remove(values.size() - 1);

					settings.put(preferenceKey, values.toArray(new String[0]));
