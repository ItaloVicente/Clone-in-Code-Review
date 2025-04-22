			try (ObjectReader reader = ins.newReader()) {
				assertSame(ins
				assertTrue(Arrays.equals(data
				assertEquals(0
			}
			ins.flush();
