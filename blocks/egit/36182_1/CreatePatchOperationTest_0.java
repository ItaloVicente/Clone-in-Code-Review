		operation.setHeaderFormat(DiffHeaderFormat.NONE);
		operation.execute(new NullProgressMonitor());

		String patchContent = operation.getPatchContent();
		assertNotNull(patchContent);
		assertPatch(SIMPLE_PATCH_CONTENT.replace("af28d38", "eb5f2c9")
				+ "\\ No newline at end of file\n",
				patchContent);
	}

	@Test
	public void testOnelineHeaderPatch() throws Exception {
		RevCommit secondCommit = testRepository.appendContentAndCommit(
				project.getProject(), file, "another line\n", "2nd commit");

		CreatePatchOperation operation = new CreatePatchOperation(
				testRepository.getRepository(), secondCommit);

