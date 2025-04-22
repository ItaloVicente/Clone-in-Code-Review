
			IPath relPath = new Path(repoPath);

			for (String segment : relPath.segments())
				for (Object child : cp.getChildren(currentNode)) {
					RepositoryTreeNode<File> childNode = (RepositoryTreeNode<File>) child;
					if (childNode.getObject().getName().equals(segment)) {
						currentNode = childNode;
						break;
					}
				}

			final RepositoryTreeNode selNode = currentNode;

			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					selectReveal(new StructuredSelection(selNode));
				}
			});

		} catch (RuntimeException rte) {
			Activator.handleError(rte.getMessage(), rte, false);
