			for (Throwable t = cie; t != null; t = t.getCause()) {
				if (t.getMessage()
						.equals(JGitText.get().tooManyIncludeRecursions)) {
					return;
				}
			}
			fail("Expected to find expected exception message: "
					+ JGitText.get().tooManyIncludeRecursions);
