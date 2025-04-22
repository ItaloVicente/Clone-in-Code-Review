				Collections.sort(nodes, new Comparator<StashedCommitNode>() {

					@Override
					public int compare(StashedCommitNode n1,
							StashedCommitNode n2) {
						return n1.getIndex() < n2.getIndex() ? 1 : -1;
					}
				});
