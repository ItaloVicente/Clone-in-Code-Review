		int startOfFileExtension = resource.getName().indexOf("."); //$NON-NLS-1$
		if (startOfFileExtension == -1) {
			textEditor.selectAll();
		} else {
			textEditor.setSelection(0, startOfFileExtension);
		}

