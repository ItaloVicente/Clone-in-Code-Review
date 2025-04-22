		int startOfFileExtension = resource.getName().lastIndexOf('.'); // $NON-NLS-1$
		if (startOfFileExtension == -1) {
			textEditor.selectAll();
		} else {
			textEditor.setSelection(0, startOfFileExtension);
		}
