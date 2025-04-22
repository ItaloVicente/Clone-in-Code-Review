		@Test
		public void testEditExample() throws Exception {
			init("z_e", true, true);

			Result result = applyPatch();
			verifyChange(result, "z_e");
		}

