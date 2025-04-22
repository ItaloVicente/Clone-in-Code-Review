			Collection<Ref> refs = new ArrayList<Ref>();

			Ref head = repo.getRef(Constants.HEAD);
			if (head != null && head.getLeaf().getName().equals(Constants.HEAD))
				refs.add(head);

