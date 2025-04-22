				String leafname = ref.getLeaf().getName();
				if (leafname.startsWith(Constants.R_REFS)
						&& leafname.equals(repository.getFullBranch())) {
					return true;
				} else {
					ObjectId objectId = ref.getLeaf().getObjectId();
					return objectId != null && objectId
							.equals(repository.resolve(Constants.HEAD));
				}
