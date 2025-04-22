		if (assume.length == 0 || !assume[0].booleanValue())
			assertTrue("Expected a match for: " + pattern + " with: " + target
					value);
		else
			assumeTrue("Expected a match for: " + pattern + " with: " + target
					value);
