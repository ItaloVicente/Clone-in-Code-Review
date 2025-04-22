				} else if (refName.equals(Constants.HEAD))
					return getDecoratedImage(image);
				else {
					String leafname = leaf.getName();
					if (leafname.startsWith(Constants.R_REFS)
							&& leafname.equals(node.getRepository()
									.getFullBranch()))
						return getDecoratedImage(image);
					else if (leaf.getObjectId().equals(
							node.getRepository().resolve(Constants.HEAD)))
						return getDecoratedImage(image);
