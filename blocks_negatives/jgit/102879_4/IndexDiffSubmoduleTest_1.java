		if (mode.equals(IgnoreSubmoduleMode.ALL))
			assertFalse("diff should be false with mode=" + mode,
					indexDiff.diff());
		else
			assertTrue("diff should be true with mode=" + mode,
					indexDiff.diff());
