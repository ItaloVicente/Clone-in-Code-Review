	@Test
	public void testComputeWorkspacePath() throws Exception {
		IPath oldPath = CreatePatchOperation.computeWorkspacePath(new Path(
				"test-file"), project.getProject());
		IPath newPath = CreatePatchOperation.computeWorkspacePath(new Path(
				"test-file"), project.getProject());
		assertPatch("test-file", oldPath.toString());
		assertPatch("test-file", newPath.toString());
	}

	@Test
	public void testComputeWorkspacePathRepoAboveProject() throws Exception {
		testRepository.disconnect(project.getProject());

		project = new TestProject(true, "repo/bundles/Project-1", true, null);
		File repo = new File(project.getProject().getLocationURI().getPath())
				.getParentFile().getParentFile();
		gitDir = new File(repo, Constants.DOT_GIT);
		testRepository = new TestRepository(gitDir);
		testRepository.connect(project.getProject());

		IPath oldPath = CreatePatchOperation.computeWorkspacePath(new Path(
				"bundles/Project-1/test-file"), project.getProject());
		IPath newPath = CreatePatchOperation.computeWorkspacePath(new Path(
				"bundles/Project-1/test-file"), project.getProject());
		assertPatch("test-file", oldPath.toString());
		assertPatch("test-file", newPath.toString());
	}

	@Test
	public void testUpdateWorkspacePatchPrefixes() throws Exception {
		File newFile = testRepository.createFile(project.getProject(), "new-file");
		testRepository.appendFileContent(newFile, "new content");
		File deletedFile = testRepository.createFile(project.getProject(), "deleted-file");
		commit = testRepository.addAndCommit(project.getProject(), deletedFile,
				"whatever");
		FileUtils.delete(deletedFile);

		DiffFormatter diffFmt = new DiffFormatter(null);
		diffFmt.setRepository(testRepository.getRepository());
		StringBuilder sb = new StringBuilder();
		sb.append("diff --git a/deleted-file b/deleted-file").append("\n");
		sb.append("deleted file mode 100644").append("\n");
		sb.append("index e69de29..0000000").append("\n");
		sb.append("--- a/deleted-file").append("\n");
		sb.append("+++ /dev/null").append("\n");
		sb.append("diff --git a/new-file b/new-file").append("\n");
		sb.append("new file mode 100644").append("\n");
		sb.append("index 0000000..47d2739").append("\n");
		sb.append("--- /dev/null").append("\n");
		sb.append("+++ b/new-file").append("\n");
		sb.append("@@ -0,0 +1 @@").append("\n");
		sb.append("+new content").append("\n");
		sb.append("\\ No newline at end of file").append("\n");
		sb.append(SIMPLE_PATCH_CONTENT);

		CreatePatchOperation op = new CreatePatchOperation(testRepository.getRepository(), null);
		op.updateWorkspacePatchPrefixes(sb, diffFmt);
		StringBuilder sb1 = new StringBuilder("### Eclipse Workspace Patch 1.0\n#P ")
				.append(project.getProject().getName()).append("\n").append(sb);

		assertPatch(SIMPLE_WORKSPACE_PATCH_CONTENT, sb1.toString());
	}

	@Test
	public void testWorkspacePatchForCommit() throws Exception {
		File deletedFile = testRepository.createFile(project.getProject(), "deleted-file");
		commit = testRepository.addAndCommit(project.getProject(), deletedFile,
				"whatever");
		FileUtils.delete(deletedFile);
		testRepository.appendFileContent(file, "another line");
		File newFile = testRepository.createFile(project.getProject(), "new-file");
		testRepository.appendFileContent(newFile, "new content");
		testRepository.untrack(deletedFile);
		testRepository.track(file);
		testRepository.track(newFile);
		commit = testRepository.commit("2nd commit");

		CreatePatchOperation operation = new CreatePatchOperation(
				testRepository.getRepository(), commit);

		operation.setHeaderFormat(DiffHeaderFormat.WORKSPACE);
		operation.execute(new NullProgressMonitor());

		assertPatch(SIMPLE_WORKSPACE_PATCH_CONTENT, operation.getPatchContent());
	}

	@Test
	public void testWorkspacePatchForWorkingDir() throws Exception {
		testRepository.addToIndex((IFile) project.getProject().findMember(".classpath"));
		testRepository.addToIndex((IFile) project.getProject().findMember(".project"));
		testRepository.commit("commit all");
		testRepository.appendFileContent(file, "another line");
		File newFile = testRepository.createFile(project.getProject(), "new-file");
		testRepository.appendFileContent(newFile, "new content");
		File deletedFile = testRepository.createFile(project.getProject(), "deleted-file");
		commit = testRepository.addAndCommit(project.getProject(), deletedFile,
				"whatever");
		FileUtils.delete(deletedFile);

		CreatePatchOperation operation = new CreatePatchOperation(
				testRepository.getRepository(), null);

		operation.setHeaderFormat(DiffHeaderFormat.WORKSPACE);
		operation.execute(new NullProgressMonitor());

		assertPatch(SIMPLE_WORKSPACE_PATCH_CONTENT, operation.getPatchContent());
	}

