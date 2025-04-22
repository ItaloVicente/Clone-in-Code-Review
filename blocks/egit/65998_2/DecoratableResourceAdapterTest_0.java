	private void assertHasUnstagedChanges(boolean expected,
			IDecoratableResource... decoratableResources) {
		for (IDecoratableResource d : decoratableResources) {
			assertTrue(d.hasUnstagedChanges() == expected);
		}
	}

