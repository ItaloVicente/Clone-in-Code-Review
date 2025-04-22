		messageLabel.getAccessible().sendEvent(ACC.EVENT_ATTRIBUTE_CHANGED,
				null);
		messageLabel.getAccessible().sendEvent(
				ACC.EVENT_TEXT_CHANGED,
				new Object[] { new Integer(ACC.TEXT_DELETE), new Integer(0),
						new Integer(oldMessage.length()), oldMessage });
		messageLabel.getAccessible().sendEvent(
				ACC.EVENT_TEXT_CHANGED,
				new Object[] { new Integer(ACC.TEXT_INSERT), new Integer(0),
						new Integer(newMessage.length()), newMessage });
