			Tree tree = gsd.mapSrcTree();

			if (tree == null)
				return new IResource[0];

			IResource[] members = ((IContainer) resource).members();
			Set<IResource> membersSet = getAllMembers(repo, tree, members);

			return membersSet.toArray(new IResource[membersSet.size()]);
