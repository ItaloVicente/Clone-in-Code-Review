		gpgSigner.setPropertyChangeListener(event -> {
			if (FieldEditor.VALUE.equals(event.getProperty())) {
				gpgExecutable.setEnabled("gpg".equals(event.getNewValue()), //$NON-NLS-1$
						generalGroup);
			}
		});
