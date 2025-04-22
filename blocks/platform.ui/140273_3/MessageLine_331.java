	public void setErrorStatus(IStatus status) {
		if (status != null) {
			String message = status.getMessage();
			if (message != null && message.length() > 0) {
				setText(message);
				setImage(findImage(status));
				return;
			}
		}
