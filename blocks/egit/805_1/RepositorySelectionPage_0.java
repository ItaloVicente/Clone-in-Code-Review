
		String preset = null;
		if (presetUri == null) {
			Clipboard clippy = new Clipboard(Display.getCurrent());
			String text = (String) clippy.getContents(TextTransfer.getInstance());
			try {
				if(Transport.canHandleProtocol(new URIish(text))) {
					preset = text;
				}
			} catch (URISyntaxException e) {
				preset = null;
			}
		}
		this.presetUri = preset;
