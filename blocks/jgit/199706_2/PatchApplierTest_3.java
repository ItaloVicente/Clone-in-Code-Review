		@Test
		public void testMultipleFilesPatch() throws Exception {
			patchName = "XAndY";
			initFile("X"
			initFile("Y"

			Result result = applyPatch();
			verifyChange(result);
		}

		@Test
		public void testDoNotAffectOtherFiles() throws Exception {
			initTest("X");
			initFile("Unaffected"

			Result result = applyPatch();

			assertEquals(1
			assertEquals("X"
			verifyContent(result
			verifyContent(result
		}
