		if (Character.isDigit(acceleratorText.charAt(0))) {
			try {
				action.setAccelerator(Integer.valueOf(acceleratorText).intValue());
			} catch (NumberFormatException e) {
				WorkbenchPlugin.log("Invalid accelerator declaration for action: " + id, e); //$NON-NLS-1$
			}
		} else {
