						if (node.getType() == RepositoryTreeNodeType.FILE)
							openFile((File) node.getObject());
						else if (node.getType() == RepositoryTreeNodeType.REF) {
							Ref ref = (Ref) node.getObject();
							if (ref.getName().startsWith(Constants.R_HEADS)
									|| ref.getName().startsWith(
											Constants.R_REMOTES))
								checkoutBranch(node, ref.getName());
						}
