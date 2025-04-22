
		bot.waitUntil(Conditions.shellCloses(createPatchWizard.getShell()));

		byte[] bytes = IO.readFully(patch);
		String patchContent = new String(bytes, "UTF-8");

		assertEquals(EXPECTED_PATCH_CONTENT, patchContent);
	}

	@Test
	public void testWorkspace() throws Exception {
		touchAndSubmit("oldContent", null);
		touch("newContent");
