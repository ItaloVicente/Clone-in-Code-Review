		int idx = scheme.getSelectionIndex();
		if (idx >= 0) {
			Protocol p = Protocol.values()[idx];
			hostText.setEnabled(p.hasHost());
			portText.setEnabled(p.hasPort());
			setEnabledRecursively(authGroup, p.canAuthenticate());
