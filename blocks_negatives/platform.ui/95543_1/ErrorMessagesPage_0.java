		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String s = text.getText();
				if (s.length() > 0 && s.length() <= 5) {
					mmng.addMessage("textLength",
							"Text is longer than 0 characters", null,
							IMessageProvider.INFORMATION, text);
				} else if (s.length() > 5 && s.length() <= 10) {
					mmng.addMessage("textLength",
							"Text is longer than 5 characters", null,
							IMessageProvider.WARNING, text);
				} else if (s.length() > 10) {
					mmng.addMessage("textLength",
							"Text is longer than 10 characters", null,
							IMessageProvider.ERROR, text);
				} else {
					mmng.removeMessage("textLength", text);
				}
				boolean badType = false;
				for (int i = 0; i < s.length(); i++) {
					if (!Character.isLetter(s.charAt(i))) {
						badType = true;
						break;
					}
				}
				if (badType) {
					mmng.addMessage("textType",
							"Text must only contain letters", null,
							IMessageProvider.ERROR, text);
				} else {
					mmng.removeMessage("textType", text);
