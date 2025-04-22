			if (!(node.getObject() instanceof Ref))
				return false;
			Ref ref = (Ref) node.getObject();
			try {
				if (ref.getName().startsWith(Constants.R_REFS)) {
					return ref.getName().equals(repository.getFullBranch());
				} else if (ref.getName().equals(Constants.HEAD)) {
					return true;
				} else {
					String leafname = ref.getLeaf().getName();
					if (leafname.startsWith(Constants.R_REFS)
							&& leafname.equals(repository.getFullBranch())) {
						return true;
					} else {
						ObjectId objectId = ref.getLeaf().getObjectId();
						return objectId != null && objectId
								.equals(repository.resolve(Constants.HEAD));
