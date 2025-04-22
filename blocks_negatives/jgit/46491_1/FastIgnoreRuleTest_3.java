		if (assume.length == 0 || !assume[0].booleanValue())
			assertFalse("Expected no match for: " + pattern + " with: " + path,
					match);
		else
			assumeFalse("Expected no match for: " + pattern + " with: " + path,
					match);
