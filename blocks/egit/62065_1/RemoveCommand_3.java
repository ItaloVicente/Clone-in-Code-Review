				for (RepositoryNode node : selectedNodes) {
					Repository repository = node.getRepository();
					Assert.isNotNull(repository);
					org.eclipse.egit.core.Activator.getDefault()
							.getIndexDiffCache().forget(repository);
					repository.close();
				}
