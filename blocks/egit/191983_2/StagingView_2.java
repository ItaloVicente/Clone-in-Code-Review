			boolean withChangeId = addChangeIdAction.isChecked();
			if (repository == currentRepository) {
				pushMode = currentPushMode.get(Boolean.valueOf(withChangeId));
				if (pushMode != null) {
					return pushMode;
				}
			}
