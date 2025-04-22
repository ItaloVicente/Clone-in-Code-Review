
			IContainer findContainer = IteratorService.findContainer(root, repository.getWorkTree());
			if (findContainer != null) {
				IResource findMember = findContainer.findMember(path);
				return findMember;
			}
