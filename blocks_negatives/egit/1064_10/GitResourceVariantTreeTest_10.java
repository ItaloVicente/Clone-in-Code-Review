	/**
	 * When we want to obtain list of members, members() method should return
	 * only members that are in repository. In this test we create Main.java
	 * file, stage it and commit it. Then we create Main2.java file with we don't
	 * add to repository. members() method should return one member because only
	 * one file is in repository.
	 * @throws Exception
	 */
	@Test
	public void shouldReturnOneMember() throws Exception {
		createResourceAndCommit("org.egit.test", "Main.java", "class Main {}",
				"Initial commit");
		IPackageFragment iPackage = project
				.createPackage("org.egit.test.nested");
		project.createType(iPackage, "Main2.java", "class Main2 {}");
		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		assertEquals(1, grvt.members(project.project).length);
		IResource[] members = grvt.members(project.project);
		assertEquals("src", members[0].getName());
	}

	/**
	 * members() method should return only members that are on same level (it
	 * cannot work recursively). In this test it should return one file and one
	 * folder member.
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void shouldReturnTwoMembers() throws Exception {
		IPackageFragment iPackage = project.createPackage("org.egit.test");
		createResourceAndCommit(iPackage, "Main.java", "class Main {}",
				"Initial commit");
		createResourceAndCommit("org.egit.test.nested", "Main2.java",
				"class Main2 {}", "Second commit");

		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		assertEquals(2, grvt.members(iPackage.getResource()).length);
		IResource[] members = grvt.members(iPackage.getResource());
		assertEquals("nested", members[0].getName());
		assertEquals("Main.java", members[1].getName());
	}

