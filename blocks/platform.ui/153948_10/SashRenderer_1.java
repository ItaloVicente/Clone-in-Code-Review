
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			Layout layout = s.getLayout();
			if (layout instanceof SashLayout) {
				if (((SashLayout) layout).layoutUpdateInProgress) {
					return;
				}
