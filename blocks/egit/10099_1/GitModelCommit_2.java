			public boolean isWorkingTree() {
				return false;
			}
		};
		TreeModelFactory treeModelFactory = new TreeModelFactory() {
			public GitModelTree createTreeModel(GitModelObjectContainer parent,
					IPath fullPath, int kind) {
				return new GitModelTree(parent, fullPath, kind);
			}
		};
		return TreeBuilder.build(this, repo, commit.getChildren(),
				fileModelFactory, treeModelFactory);
