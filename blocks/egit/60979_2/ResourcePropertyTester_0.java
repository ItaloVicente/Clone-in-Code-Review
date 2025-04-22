			if ("isStaged".equals(property)) { //$NON-NLS-1$
				return ResourceStateFactory.getInstance().get(res)
						.staged() != Staged.NOT_STAGED;
			} else if ("isIgnored".equals(property)) { //$NON-NLS-1$
				return ResourceStateFactory.getInstance().get(res).isIgnored();
			} else if ("hasUnstagedChanges".equals(property)) { //$NON-NLS-1$
				IResourceState state = ResourceStateFactory.getInstance()
						.get(res);
				return !state.isTracked() || state.isDirty();
			} else {
				return testRepositoryState(repository, property);
			}
