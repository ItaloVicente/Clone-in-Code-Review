			Control messageControl = null;
			if (this.linkSelectionListeners == null || this.linkSelectionListeners.isEmpty()) {
				messageLabel = new Label(composite, getMessageLabelStyle());
				messageLabel.setText(message);
				messageControl = messageLabel;
			} else {
				messageLink = new Link(composite, getMessageLabelStyle());
				messageLink.setText(message);
				for (SelectionListener listener : this.linkSelectionListeners) {
					messageLink.addSelectionListener(listener);
				}
				messageControl = messageLink;
			}
