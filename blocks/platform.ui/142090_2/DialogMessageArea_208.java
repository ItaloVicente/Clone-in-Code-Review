		messageComposite.setVisible(true);
		titleLabel.setVisible(false);
		String shortText = Dialog.shortenText(newMessage,messageText);
		if (newMessage.equals(messageText.getToolTipText())
				&& newImage == messageImageLabel.getImage()
					&& shortText.equals(messageText.getText())) {
			return;
		}
		messageImageLabel.setImage(newImage);
		messageText.setText(Dialog.shortenText(newMessage,messageText));
		messageText.setToolTipText(newMessage);
		lastMessageText = newMessage;
