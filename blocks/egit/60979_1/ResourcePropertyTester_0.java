			if ("isStaged".equals(property)) { //$NON-NLS-1$
				IndexDiffData indexDiffData = ResourceStateFactory.getInstance()
						.getIndexDiffDataOrNull(res);
				if (indexDiffData != null) {
					return ResourceStateFactory.getInstance()
							.get(indexDiffData, res)
							.staged() != Staged.NOT_STAGED;
				}
			} else if ("isIgnored".equals(property)) { //$NON-NLS-1$
				IndexDiffData indexDiffData = ResourceStateFactory.getInstance()
						.getIndexDiffDataOrNull(res);
				if (indexDiffData != null) {
					return ResourceStateFactory.getInstance()
							.get(indexDiffData, res).isIgnored();
				}
			} else if ("hasUnstagedChanges".equals(property)) { //$NON-NLS-1$
				IndexDiffData indexDiffData = ResourceStateFactory.getInstance()
						.getIndexDiffDataOrNull(res);
				if (indexDiffData != null) {
					IResourceState state = ResourceStateFactory.getInstance()
							.get(indexDiffData, res);
					return !state.isTracked() || state.isDirty();
				}
			} else {
				return testRepositoryState(repository, property);
			}
