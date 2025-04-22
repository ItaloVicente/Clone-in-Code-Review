		if (dialogSettings == null) {
			return;
		}

		try {
			IPath path = getStateLocationOrNull();
			if (path == null) {
				return;
			}
			String readWritePath = path.append(FN_DIALOG_SETTINGS).toOSString();
			dialogSettings.save(readWritePath);
		} catch (IOException | IllegalStateException e) {
		}
