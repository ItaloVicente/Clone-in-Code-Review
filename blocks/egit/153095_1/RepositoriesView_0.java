			if (!(selected instanceof RepositoryNode
					|| selected instanceof RepositoryVirtualNode)) {
				File file = Adapters.adapt(selected, File.class);
				if (file != null) {
					IPath path = new Path(file.getAbsolutePath());
					showPaths(Arrays.asList(path));
					return;
				}
