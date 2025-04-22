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
		File newFile = testRepository.createFile(project.getProject(), "new-file");
		testRepository.appendFileContent(newFile, "new content");
		File deletedFile = testRepository.createFile(project.getProject(), "deleted-file");
		commit = testRepository.addAndCommit(project.getProject(), deletedFile,
				"whatever");
		FileUtils.delete(deletedFile);

		DiffFormatter diffFmt = new DiffFormatter(null);
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

		CreatePatchOperation.updateWorkspacePatchPrefixes(sb, project
				.getProject().findMember("test-file"), diffFmt);
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
				testRepository.getRepository(), commit, project.getProject());

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
				testRepository.getRepository(), null, project.getProject());

		operation.setHeaderFormat(DiffHeaderFormat.WORKSPACE);
		operation.execute(new NullProgressMonitor());

		assertPatch(SIMPLE_WORKSPACE_PATCH_CONTENT, operation.getPatchContent());
	}

