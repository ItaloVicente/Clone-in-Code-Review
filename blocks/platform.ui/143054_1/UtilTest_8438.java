		assertTrue(boundActivities.size() == 1);
		String id = boundActivities.iterator().next().toString();
		assertTrue(id.equals(ACTIVITY_NON_REG_EXP));

		Pattern pattern = binding.getPattern();
		assertTrue(pattern.pattern().equals(
