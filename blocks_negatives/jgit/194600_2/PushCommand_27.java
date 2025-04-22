				refSpecs.addAll(config.getPushRefSpecs());
			}
			if (refSpecs.isEmpty()) {
				Ref head = repo.exactRef(Constants.HEAD);
				if (head != null && head.isSymbolic())
					refSpecs.add(new RefSpec(head.getLeaf().getName()));
