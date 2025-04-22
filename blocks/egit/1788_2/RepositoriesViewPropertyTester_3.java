				if (ref.getName().startsWith(Constants.R_REFS)) {
					return ref.getName().equals(
							node.getRepository().getFullBranch());
				} else if (ref.getName().equals(Constants.HEAD))
					return true;
				else {
					String leafname = ref.getLeaf().getName();
					if (leafname.startsWith(Constants.R_REFS)
							&& leafname.equals(
									node.getRepository().getFullBranch()))
						return true;
					else
						ref.getLeaf().getObjectId().equals(
								node.getRepository().resolve(Constants.HEAD));
				}
