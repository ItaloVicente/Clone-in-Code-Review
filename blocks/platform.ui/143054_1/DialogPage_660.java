		imageDescriptor = desc;
		if (image != null) {
			image.dispose();
			image = null;
		}
	}

	public void setMessage(String newMessage) {
		setMessage(newMessage, NONE);
	}

	public void setMessage(String newMessage, int newType) {
		message = newMessage;
		messageType = newType;
	}

	@Override
