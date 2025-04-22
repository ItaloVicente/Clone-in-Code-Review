				for (Map.Entry<RepositoryMapping, List<IFile>> target : targets
						.entrySet()) {
					RepositoryMapping mapping = target.getKey();
					Repository repository = mapping.getRepository();
					List<IFile> filesList = target.getValue();
					IFile[] filesToCommit = filesList
							.toArray(new IFile[filesList.size()]);
