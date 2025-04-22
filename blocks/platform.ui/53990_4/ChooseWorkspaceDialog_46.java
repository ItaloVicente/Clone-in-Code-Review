        text.addModifyListener(e -> {
			Button okButton = getButton(Window.OK);
			if(okButton != null && !okButton.isDisposed()) {
				boolean nonWhitespaceFound = false;
				String characters = getWorkspaceLocation();
				for (int i = 0; !nonWhitespaceFound
						&& i < characters.length(); i++) {
					if (!Character.isWhitespace(characters.charAt(i))) {
						nonWhitespaceFound = true;
