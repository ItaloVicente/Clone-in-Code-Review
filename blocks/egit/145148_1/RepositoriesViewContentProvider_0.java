			return getRefs(repo, prefix).entrySet().stream()
					.filter(e -> !e.getValue().isSymbolic()).map(e -> {
						int i = e.getKey().indexOf('/', prefix.length());
						if (i < 0) {
							return new RefNode(node, repo, e.getValue());
						} else {
							return new BranchHierarchyNode(node, repo,
									Path.fromPortableString(
											prefix + e.getKey().substring(
													prefix.length(), i + 1)));
						}
					}).toArray();
