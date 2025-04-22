	public void testErrorCondition() {
		try {
			WorkbenchPlugin.createExtension(null, null);
			fail("createExtension with nulls succeeded");
		} catch (CoreException e) {
			assertNotNull("Cause is null", e.getStatus().getException());
		} catch (Throwable t) {
			fail("Throwable not wrapped in core exception.");
		}
	}
