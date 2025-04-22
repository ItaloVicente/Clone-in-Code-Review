		this.location = new Path(repo.getWorkTree().toString());

		this.children = TreeBuilder.build(this, repo, changes, fileFactory,
				new TreeModelFactory() {
					public GitModelTree createTreeModel(
							GitModelObjectContainer parentObject,
							IPath fullPath,
							int kind) {
						return new GitModelCacheTree(parentObject, repo,
								fullPath, fileFactory);
					}
				});
