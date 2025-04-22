
		operation = new CreatePatchOperation(
				testRepository.getRepository(), secondCommit);

		operation.setHeaderFormat(DiffHeaderFormat.EMAIL);
		operation.execute(new NullProgressMonitor());

		patchContent = operation.getPatchContent();
		assertNotNull(patchContent);
		assertGitPatch(SIMPLE_GIT_PATCH_CONTENT, patchContent);
