	@Test
	public void reflogSeek() throws IOException {
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";
		String msgNext = "test next";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(buffer);

		writer.writeLog(MASTER
		writer.writeLog(NEXT

		writer.finish();
		byte[] table = buffer.toByteArray();

		ReftableReader t = read(table);
		try (LogCursor c = t.seekLog(MASTER
			assertTrue(c.next());
			assertEquals(c.getReflogEntry().getComment()
		}
		try (LogCursor c = t.seekLog(MASTER
			assertFalse(c.next());
		}
		try (LogCursor c = t.seekLog(MASTER
			assertTrue(c.next());
			assertEquals(c.getUpdateIndex()
			assertEquals(c.getReflogEntry().getComment()
		}
		try (LogCursor c = t.seekLog(NEXT
			assertTrue(c.next());
			assertEquals(c.getReflogEntry().getComment()
		}
		try (LogCursor c = t.seekLog(NEXT
			assertFalse(c.next());
		}
		try (LogCursor c = t.seekLog(NEXT
			assertTrue(c.next());
			assertEquals(c.getUpdateIndex()
			assertEquals(c.getReflogEntry().getComment()
		}
	}

