	private void addValueToPrefs(String value, String prefsName) {
		if (value.length() > 0) {
			IDialogSettings settings = org.eclipse.egit.ui.Activator
					.getDefault().getDialogSettings();
			String[] existingValues = settings.getArray(prefsName);
			if (existingValues == null) {
				existingValues = new String[] { value };
				settings.put(prefsName, existingValues);
			} else {

				List<String> values = new ArrayList<String>(
						existingValues.length + 1);

				for (String existingValue : existingValues)
					values.add(existingValue);
				if (values.indexOf(value) == 0)
					return;

				values.remove(value);
				values.add(0, value);

				while (values.size() > 10)
					values.remove(values.size() - 1);

				settings.put(prefsName, values
						.toArray(new String[values.size()]));
			}
		}
	}

