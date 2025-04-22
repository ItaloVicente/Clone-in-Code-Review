			ListBranchCommand command = new Git(db).branchList();
			if (all)
				command.setListMode(ListMode.ALL);
			else if (remote)
				command.setListMode(ListMode.REMOTE);

			if (containsCommitish != null)
				command.setContains(containsCommitish);

			List<Ref> refs = command.call();
			for (Ref ref : refs) {
				if (ref.getName().equals(Constants.HEAD))
					addRef("(no branch)"
			}

			addRefs(refs
			addRefs(refs
