		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			assertEquals("Yes", left.getText());
			assertEquals("No", middle.getText());
			assertEquals("Cancel", right.getText());
		} else {
			assertEquals("No", left.getText());
			assertEquals("Cancel", middle.getText());
			assertEquals("Yes", right.getText());
		}

