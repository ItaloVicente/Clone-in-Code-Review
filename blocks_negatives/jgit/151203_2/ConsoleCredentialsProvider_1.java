			} else {
				return false;
			}
		} else {
			String v = cons.readLine("%s: ", item.getPromptText()); //$NON-NLS-1$
			if (v != null) {
				item.setValue(v);
				return true;
			} else {
				return false;
