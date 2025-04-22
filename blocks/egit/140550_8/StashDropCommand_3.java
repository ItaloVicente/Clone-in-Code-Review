				Collections
						.sort(nodes,
								(StashedCommitNode n1,
										StashedCommitNode n2) -> n1
												.getIndex() < n2.getIndex() ? 1
														: -1);
