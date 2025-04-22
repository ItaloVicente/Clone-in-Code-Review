			try {
				_p1 = ResourcesPlugin.getWorkspace().getRoot().getProject("p1");
				_p1.open(null);
				_p2 = ResourcesPlugin.getWorkspace().getRoot().getProject("p2");
				_p2.open(null);
			} catch (CoreException e) {
				fail("Should not throw an exception");
			}

