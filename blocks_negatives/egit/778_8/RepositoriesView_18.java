				final List<String> directories = getDirs();

				boolean needsNewInput = tv.getInput() == null;
				List<RepositoryTreeNode<Repository>> oldInput = (List) tv
						.getInput();
				if (!needsNewInput)
					needsNewInput = oldInput.size() != directories.size();

				if (!needsNewInput) {
					List<String> oldDirectories = new ArrayList<String>();
					for (RepositoryTreeNode<Repository> node : oldInput) {
						oldDirectories.add(node.getRepository().getDirectory()
								.getPath());
					}
					needsNewInput = !directories.containsAll(oldDirectories);
				}
