			assertEquals("Rejected by \"pre-commit\" hook.\nstderr\n"
					e.getMessage()
					"unexpected error message from pre-commit hook");
			assertEquals("test\n"
					out.toString(UTF_8)
					"unexpected output from pre-commit hook");
