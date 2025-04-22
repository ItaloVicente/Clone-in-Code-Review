	@Test
	public void shouldResolveAsRelativePath() {
		IProject proj = project.getProject();
		IPath projectPath = proj.getLocation()
				.removeTrailingSeparator();
		String gitHereTest = ".git";
		String gitTest = "../../.git";
		String gitSubdirTest = "foobar/.git";
		String gitSubmoduleTest = "../../.git/modules/submodule";
		String gitAbsolute = projectPath.uptoSegment(0)
				.append(projectPath.segment(0) + "fake").append(".git")
				.toOSString();
		String parents = "";
		while (projectPath.segmentCount() > 2) {
			String pathString = projectPath.toOSString();
			assertRepoMappingPath(proj, pathString, gitHereTest, parents);
			assertRepoMappingPath(proj, pathString, gitTest, parents);
			assertRepoMappingPath(proj, pathString, gitSubdirTest, parents);
			assertRepoMappingPath(proj, pathString, gitSubmoduleTest, parents);
			assertRepoMappingPath(proj, pathString, gitAbsolute, "");
			projectPath = projectPath.removeLastSegments(1);
			parents += "../";
		}
	}

	private void assertRepoMappingPath(IProject testProject, String path,
			String testDir, String parents) {
		String testDirOS = testDir.replace('/', File.separatorChar);
		File testFile = new File(testDirOS);
		if (!testFile.isAbsolute()) {
			testFile = new File(new File(path), testDirOS);
		}
		RepositoryMapping mapping = RepositoryMapping.create(testProject,
				testFile);
		assertEquals(parents + testDir,
				mapping.getGitDirPath().toPortableString());
	}

