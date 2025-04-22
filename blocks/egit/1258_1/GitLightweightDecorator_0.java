						IContainer parent = resource.getParent();
						if (parent.exists())
							resourcesToUpdate.addAll(Arrays.asList(parent
									.members()));
						else
							return false;
