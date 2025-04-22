			if (refSpecs.isEmpty()) {
				Ref head = repo.getRef(Constants.HEAD);
				if (head != null && head.isSymbolic())
					refSpecs.add(new RefSpec(head.getLeaf().getName()));
			}

