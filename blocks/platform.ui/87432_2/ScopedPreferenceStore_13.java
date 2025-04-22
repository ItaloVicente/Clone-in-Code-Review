			preferencesListener = event -> {

				if (silentRunning) {
					return;
				}

				Object oldValue = event.getOldValue();
				Object newValue = event.getNewValue();
				String key = event.getKey();
				if (newValue == null) {
					newValue = getDefault(key, oldValue);
				} else if (oldValue == null) {
					oldValue = getDefault(key, newValue);
