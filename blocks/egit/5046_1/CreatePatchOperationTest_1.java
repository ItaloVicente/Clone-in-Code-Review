	@Test
	public void testProcessPath() throws Exception {
		DiffFormatter diffFmt = new DiffFormatter(null);
		IPath oldPath = CreatePatchOperation.processPath(
				new Path("a/test-file"), project.getProject(),
				new Path(diffFmt.getOldPrefix()));
		IPath newPath = CreatePatchOperation.processPath(
				new Path("b/test-file"), project.getProject(),
				new Path(diffFmt.getNewPrefix()));
		assertPatch("test-file", oldPath.toString());
		assertPatch("test-file", newPath.toString());
	}

	@Test
	public void testProcessPathRepoAboveProject() throws Exception {
		testRepository.disconnect(project.getProject());

		project = new TestProject(true, "repo/bundles/Project-1", true, null);
		File repo = new File(project.getProject().getLocationURI().getPath()).getParentFile().getParentFile();
		gitDir = new File(repo, Constants.DOT_GIT);
		testRepository = new TestRepository(gitDir);
		testRepository.connect(project.getProject());


		DiffFormatter diffFmt = new DiffFormatter(null);
		IPath oldPath = CreatePatchOperation.processPath(new Path(
				"a/bundles/Project-1/test-file"), project.getProject(), new Path(
				diffFmt.getOldPrefix()));
		IPath newPath = CreatePatchOperation.processPath(new Path(
				"b/bundles/Project-1/test-file"), project.getProject(), new Path(
				diffFmt.getNewPrefix()));
		assertPatch("test-file", oldPath.toString());
		assertPatch("test-file", newPath.toString());
	}

	@Test
	public void testUpdateWorkspacePatchPrefixes() throws Exception {
		DiffFormatter diffFmt = new DiffFormatter(null);
		StringBuilder sb = new StringBuilder(SIMPLE_PATCH_CONTENT);
		CreatePatchOperation.updateWorkspacePatchPrefixes(sb, project
				.getProject().findMember("test-file"), diffFmt);
		StringBuilder sb1 = new StringBuilder("### Eclipse Workspace Patch 1.0\n#P ")
				.append(project.getProject().getName()).append("\n").append(sb);

		assertPatch(SIMPLE_WORKSPACE_PATCH_CONTENT, sb1.toString());
	}

	@Test
	public void testWorkspacePatch() throws Exception {
		RevCommit secondCommit = testRepository.appendContentAndCommit(
				project.getProject(), file, "another line", "2nd commit");

		CreatePatchOperation operation = new CreatePatchOperation(
				testRepository.getRepository(), secondCommit, project.getProject());

		operation.setHeaderFormat(DiffHeaderFormat.WORKSPACE);
		operation.execute(new NullProgressMonitor());

		String patchContent = operation.getPatchContent();
		assertNotNull(patchContent);
		assertPatch(SIMPLE_WORKSPACE_PATCH_CONTENT, patchContent);
	}

