
		ReftableReader t = read(table);
		for (Ref exp : refs) {
			try (RefCursor rc = t.seek(exp.getName())) {
				assertTrue("has " + exp.getName(), rc.next());
				Ref act = rc.getRef();
				assertEquals(exp.getName(), act.getName());
				assertEquals(exp.getObjectId(), act.getObjectId());
				assertFalse(rc.next());
			}
		}
