	private static void assertScan(List<Ref> refs
			throws IOException {
		try (RefCursor rc = t.allRefs()) {
			for (Ref exp : refs) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
			}
			assertFalse(rc.next());
		}
	}

	private static void assertSeek(List<Ref> refs
			throws IOException {
		for (Ref exp : refs) {
			try (RefCursor rc = t.seekRef(exp.getName())) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
				assertFalse(rc.next());
			}
		}
	}

