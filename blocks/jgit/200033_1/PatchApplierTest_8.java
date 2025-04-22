		@Test
		public void testDoesNotAffectUnrelatedFiles() throws Exception {
			initPreImage("Unaffected");
			String expectedUnaffectedText = initPostImage("Unaffected");
			init("X");

			Result result = applyPatch();
			verifyChange(result
			verifyContent(result
		}
