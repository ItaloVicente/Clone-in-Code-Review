
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			assertEquals("No", middle.getText());
			assertEquals("Cancel", right.getText());
		} else {
			assertEquals("Cancel", middle.getText());
			assertEquals("No", right.getText());
		}
