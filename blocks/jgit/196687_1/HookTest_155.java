		assertEquals("test arg1 arg2\nstdin\n"
				out.toString(UTF_8)
				"unexpected hook output");
		assertEquals("stderr\n"
				err.toString(UTF_8)
				"unexpected output on stderr stream");
		assertEquals(0
		assertEquals(ProcessResult.Status.OK
				res.getStatus()
				"unexpected process status");
