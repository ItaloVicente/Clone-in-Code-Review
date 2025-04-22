		try (RevWalk rw = new RevWalk(repository)) {
			monitor.beginTask(
					UIText.GitCompareEditorInput_CompareResourcesTaskName,
					IProgressMonitor.UNKNOWN);

			for (IResource resource : resources) {
				if (resource == null) {
					continue;
				}
				RepositoryMapping map = RepositoryMapping.getMapping(resource);
				if (map == null) {
					throw new InvocationTargetException(
							new IllegalStateException(
									UIText.GitCompareEditorInput_ResourcesInDifferentReposMessagge));
				}
				if (repository != null && repository != map.getRepository())
					throw new InvocationTargetException(
							new IllegalStateException(
									UIText.GitCompareEditorInput_ResourcesInDifferentReposMessagge));
				String repoRelativePath = map.getRepoRelativePath(resource);
				filterPathStrings.add(repoRelativePath);
				DiffNode node = new DiffNode(Differencer.NO_CHANGE) {
					@Override
					public Image getImage() {
						return FOLDER_IMAGE;
					}
				};
				diffRoots.put(new Path(map.getRepoRelativePath(resource)),
						node);
				repository = map.getRepository();
			}

			if (repository == null)
				throw new InvocationTargetException(new IllegalStateException(
						UIText.GitCompareEditorInput_ResourcesInDifferentReposMessagge));

			if (monitor.isCanceled())
				throw new InterruptedException();

			final RevCommit baseCommit;
			try {
				try {
					baseCommit = rw
							.parseCommit(repository.resolve(baseVersion));
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				}

				final RevCommit compareCommit;
				if (compareVersion == null) {
					compareCommit = null;
				} else {
					try {
						compareCommit = rw.parseCommit(
								repository.resolve(compareVersion));
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					}
				}
				if (monitor.isCanceled())
					throw new InterruptedException();

				CompareConfiguration config = getCompareConfiguration();
				config.setLeftLabel(compareVersion);
				config.setRightLabel(baseVersion);
				if (resources.length == 0) {
					Object[] titleParameters = new Object[] {
							RepositoryUtil.INSTANCE
									.getRepositoryName(repository),
							CompareUtils.truncatedRevision(compareVersion),
							CompareUtils.truncatedRevision(baseVersion) };
					setTitle(NLS.bind(UIText.GitCompareEditorInput_EditorTitle,
							titleParameters));
				} else if (resources.length == 1) {
					Object[] titleParameters = new Object[] {
							resources[0].getFullPath().makeRelative()
									.toString(),
							CompareUtils.truncatedRevision(compareVersion),
							CompareUtils.truncatedRevision(baseVersion) };
					setTitle(NLS.bind(
							UIText.GitCompareEditorInput_EditorTitleSingleResource,
							titleParameters));
				} else {
					setTitle(NLS
							.bind(UIText.GitCompareEditorInput_EditorTitleMultipleResources,
									CompareUtils.truncatedRevision(
											compareVersion),
							CompareUtils.truncatedRevision(baseVersion)));
				}

				try {
					return buildDiffContainer(baseCommit, compareCommit,
							monitor);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				}
			} finally {
				monitor.done();
			}
