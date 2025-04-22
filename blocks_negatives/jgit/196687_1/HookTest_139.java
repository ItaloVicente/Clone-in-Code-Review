		assertEquals("unexpected hook output", "test arg1 arg2\nstdin\n",
				out.toString(UTF_8));
		assertEquals("unexpected output on stderr stream", "stderr\n",
				err.toString(UTF_8));
		assertEquals("unexpected exit code", 0, res.getExitCode());
		assertEquals("unexpected process status", ProcessResult.Status.OK,
				res.getStatus());
