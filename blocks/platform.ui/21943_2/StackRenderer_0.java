			final Save[] response;
			if (toPrompt.size() > 1) {
				response = saveHandler.promptToSave(toPrompt);
			} else if (toPrompt.size() == 1) {
				response = new Save[] { saveHandler.promptToSave(toPrompt
						.get(0)) };
			} else {
				response = new Save[] {};
			}
