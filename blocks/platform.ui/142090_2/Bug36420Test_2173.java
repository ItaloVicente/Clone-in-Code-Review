		boolean found = false;
		while (keyBindingItr.hasNext()) {
			KeySequence keyBinding = keyBindingItr.next();
			String currentText = keyBinding.toString();
			if (keySequenceText.equals(currentText)) {
				found = true;
			}
		}
