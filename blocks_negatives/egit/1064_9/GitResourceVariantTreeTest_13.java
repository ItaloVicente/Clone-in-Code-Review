	private IType createResourceAndCommit(String packageName, String fileName,
			String fileContent, String commitMsg) throws Exception {
		IPackageFragment iPackage = project.createPackage(packageName);
		return createResourceAndCommit(iPackage, fileName, fileContent,
				commitMsg);
	}

	private IType createResourceAndCommit(IPackageFragment iPackage,
			String fileName, String fileContent, String commitMsg)
			throws Exception {
		IType mainJava = project.createType(iPackage, fileName, fileContent);
		addAndCommitResource(mainJava, commitMsg);

		return mainJava;
	}

	private void addAndCommitResource(IType mainJava, String commitMsg)
			throws Exception {
		List<IResource> resources = new ArrayList<IResource>();
		resources.add(mainJava.getResource());
	}

	private void createBranch(String branchName) throws Exception {
		RefUpdate updateRef;
		updateRef = repo.updateRef(Constants.R_HEADS + branchName);
		Ref startRef = repo.getRef(branchName);
		ObjectId startAt = repo.resolve(Constants.HEAD);
		String startBranch;
		if (startRef != null)
			startBranch = branchName;
		else
			startBranch = startAt.name();
		startBranch = repo.shortenRefName(startBranch);
		updateRef.setNewObjectId(startAt);
		updateRef
				.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
		updateRef.update();
