				final List<String> directories = getDirs();

				boolean needsNewInput = tv.getInput() == null;
				List<RepositoryTreeNode<Repository>> oldInput = (List) tv
						.getInput();
				if (!needsNewInput)
					needsNewInput = oldInput.size() != directories.size();
