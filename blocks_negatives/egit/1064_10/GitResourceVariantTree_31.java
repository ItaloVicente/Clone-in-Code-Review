	private Set<IResource> getAllMembers(Repository repo, Tree tree,
			IResource[] members) throws IOException, TeamException {
		Set<IResource> membersSet = new HashSet<IResource>();

		for (IResource member : members) {
			String memberRelPath = getMemberRelPath(repo, member);
