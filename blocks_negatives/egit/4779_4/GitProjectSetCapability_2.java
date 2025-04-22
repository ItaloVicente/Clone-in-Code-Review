
	private TeamException throwTeamException(Throwable th) throws TeamException{
		Throwable current = th;
		while(current.getCause()!=null){
			current = current.getCause();
		}
		throw new TeamException(current.getMessage(), current);
	}

	/**
	 * @param gitUrl
	 * @param branch the branch to check out
	 * @param allBranches all branches which should be checked out for this gitUrl
	 * @return the directory where the project should be checked out
	 */
	private static IPath getWorkingDir(URIish gitUrl, String branch, Set<String> allBranches) {
		final IPath workspaceLocation = ResourcesPlugin.getWorkspace()
				.getRoot().getRawLocation();
		final String humanishName = gitUrl.getHumanishName();
		String extendedName;
		if (allBranches.size() == 1 || branch.equals(Constants.MASTER))
			extendedName = humanishName;
		else
		final IPath workDir = workspaceLocation.append(extendedName);
		return workDir;
	}

