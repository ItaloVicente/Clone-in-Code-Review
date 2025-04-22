
		for (Ref exp : refs) {
 			try (LogCursor lc = t.seekLog(exp.getName())) {
				assertTrue("has " + exp.getName()
			}
		}
