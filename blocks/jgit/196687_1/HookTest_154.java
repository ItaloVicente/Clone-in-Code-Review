			assertEquals("Rejected by \"commit-msg\" hook.\nstderr\n"
					e.getMessage()
					"unexpected error message from commit-msg hook");
			assertEquals("test\n"
					out.toString(UTF_8)
					"unexpected output from commit-msg hook");
