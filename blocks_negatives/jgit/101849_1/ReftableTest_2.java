
		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			for (Ref exp : refs) {
				assertTrue("has " + exp.getName(), rc.next());
				Ref act = rc.getRef();
				assertEquals(exp.getName(), act.getName());
				assertEquals(exp.getObjectId(), act.getObjectId());
			}
			assertFalse(rc.next());
		}
