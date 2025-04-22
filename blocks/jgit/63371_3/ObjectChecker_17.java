		if (windows) {
			if (raw[end - 1] == ' ' || raw[end - 1] == '.') {
				report(WIN32_BAD_NAME
						JGitText.get().corruptObjectInvalidNameEnd
						Character.valueOf(((char) raw[end - 1]))));
			}
			if (end - ptr >= 3) {
				checkNotWindowsDevice(raw
