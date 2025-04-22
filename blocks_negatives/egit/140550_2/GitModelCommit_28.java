		TreeModelFactory treeModelFactory = new TreeModelFactory() {
			@Override
			public GitModelTree createTreeModel(GitModelObjectContainer parent,
					IPath fullPath, int kind) {
				return new GitModelTree(parent, fullPath, kind);
			}
		};
