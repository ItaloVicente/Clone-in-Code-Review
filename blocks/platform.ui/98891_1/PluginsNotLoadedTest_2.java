		}
		assertTrue("Wrong bundles in active status:\n" + buf, buf.length() == 0);
	}


	@Test
	public void activePluginsShouldNotIncrease() {
		printPluginStatus(true);
		StringBuffer buf = new StringBuffer();
		for (String element : ACTIVE_BUNDLES) {
			Bundle bundle = Platform.getBundle(element);
			if (bundle == null) {
			} else if (bundle.getState() != Bundle.ACTIVE) {
				buf.append("- ");
				buf.append(element);
				buf.append('\n');
			}
