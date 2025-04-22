	@Test
	public void testOnelineHeaderPatch() throws Exception {
		RevCommit secondCommit = testRepository.appendContentAndCommit(
				project.getProject(), file, "another line", "2nd commit");

		CreatePatchOperation operation = new CreatePatchOperation(
				testRepository.getRepository(), secondCommit);

		operation.setHeaderFormat(DiffHeaderFormat.ONELINE);
		operation.execute(new NullProgressMonitor());

		String patchContent = operation.getPatchContent();
		assertNotNull(patchContent);
		assertPatch(SIMPLE_ONELINE_PATCH_CONTENT, patchContent);
	}

