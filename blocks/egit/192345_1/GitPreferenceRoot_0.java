		if (defaultSshAgent != null) {
			useSshAgent.setPropertyChangeListener(event -> {
				if (FieldEditor.VALUE.equals(event.getProperty())) {
					defaultSshAgent.setEnabled(
							((Boolean) event.getNewValue()).booleanValue(),
							remoteConnectionsGroup);
				}
			});
		}
