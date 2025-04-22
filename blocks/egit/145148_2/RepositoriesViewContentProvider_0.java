			Set<String> folderChildren = new HashSet<>();
			return getRefs(repo, prefix).entrySet().stream()
					.filter(e -> !e.getValue().isSymbolic()).map(e -> {
						int i = e.getKey().indexOf('/', prefix.length());
						if (i < 0) {
							return new RefNode(node, repo, e.getValue());
						} else {
							String name = e.getKey().substring(prefix.length(),
									i);
							if (folderChildren.add(name)) {
								return new BranchHierarchyNode(node, repo,
										Path.fromPortableString(prefix + name));
							}
							return null;
						}
					}).filter(Objects::nonNull).toArray();
