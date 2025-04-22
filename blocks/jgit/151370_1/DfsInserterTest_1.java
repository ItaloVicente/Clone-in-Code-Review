			try (ObjectReader reader = ins.newReader()) {
				assertSame(ins
				assertEquals("foo"
				assertEquals("bar"
				assertEquals(0
				ins.flush();
				assertEquals(1
			}
		}
