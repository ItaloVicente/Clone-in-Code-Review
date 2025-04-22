		try {
			commitOperation.execute(null);
			fail("expected CoreException");
		} catch (CoreException e) {
			assertEquals("No changes", e.getCause().getMessage());
		}
