		case PROJECTS: {
			List<RepositoryTreeNode<File>> projects = new ArrayList<RepositoryTreeNode<File>>();

			Collection<File> result = new HashSet<File>();
			Set<String> traversed = new HashSet<String>();
			collectProjectFilesFromDirectory(result, repo.getDirectory()
					.getParentFile(), traversed, new NullProgressMonitor());
			for (File file : result) {
				projects.add(new RepositoryTreeNode<File>(node,
						RepositoryTreeNodeType.PROJ, repo, file));
			}

			Comparator<RepositoryTreeNode<File>> sorter = new Comparator<RepositoryTreeNode<File>>() {

				public int compare(RepositoryTreeNode<File> o1,
						RepositoryTreeNode<File> o2) {
					return o1.getObject().getName().compareTo(
							o2.getObject().getName());
				}
			};
			Collections.sort(projects, sorter);

			return projects.toArray();
		}

