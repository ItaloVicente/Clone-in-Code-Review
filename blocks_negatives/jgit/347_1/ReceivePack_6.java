		sendMessage("remote", what);
	}

	private void sendMessage(final String type, final String what) {
		if (msgs != null)
			msgs.println(type + ": " + what);
