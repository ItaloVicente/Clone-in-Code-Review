		IStructuredSelection sel = null;
		try {
			sel = new StructuredSelection(((IContainer) _p2.members()[1]).members()[0]);
		} catch (CoreException e) {
			fail("Should not throw an exception");
		}
