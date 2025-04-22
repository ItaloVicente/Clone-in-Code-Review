		case WORKINGDIR: {
			List<RepositoryTreeNode<File>> children = new ArrayList<>();

			if (node.getRepository().isBare())
				return children.toArray();
			File workingDir = repo.getWorkTree();
			if (!workingDir.exists())
				return children.toArray();

			File[] childFiles = workingDir.listFiles();
			Arrays.sort(childFiles, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					if (o1.isDirectory()) {
						if (o2.isDirectory()) {
							return o1.compareTo(o2);
						}
						return -1;
					} else if (o2.isDirectory()) {
						return 1;
					}
					return o1.compareTo(o2);
				}
			});
			for (File file : childFiles) {
				if (file.isDirectory()) {
					children.add(new FolderNode(node, repo, file));
				} else {
					children.add(new FileNode(node, repo, file));
				}
			}

			return children.toArray();
		}

		case FOLDER: {
			List<RepositoryTreeNode<File>> children = new ArrayList<>();

			File parent = ((File) node.getObject());

			File[] childFiles = parent.listFiles();
			if (childFiles == null)
				return children.toArray();

			Arrays.sort(childFiles, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					if (o1.isDirectory()) {
						if (o2.isDirectory()) {
							return o1.compareTo(o2);
						}
						return -1;
					} else if (o2.isDirectory()) {
						return 1;
					}
					return o1.compareTo(o2);
				}
			});
			for (File file : childFiles) {
				if (file.isDirectory()) {
					children.add(new FolderNode(node, repo, file));
				} else {
					children.add(new FileNode(node, repo, file));
				}
