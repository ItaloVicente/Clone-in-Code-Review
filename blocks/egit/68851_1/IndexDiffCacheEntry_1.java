		indexChangedListenerHandles.add(repository.getListenerList()
				.addIndexChangedListener(indexChangedListener));
		refsChangedListenerHandles.add(repository.getListenerList()
				.addRefsChangedListener(refsChangedListener));
		try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)) {
			while (walk.next()) {
				Repository submodule = walk.getRepository();
				if (submodule != null) {
					Repository cached = org.eclipse.egit.core.Activator
							.getDefault().getRepositoryCache().lookupRepository(
									submodule.getDirectory().getAbsoluteFile());
					indexChangedListenerHandles.add(cached.getListenerList()
							.addIndexChangedListener(indexChangedListener));
					refsChangedListenerHandles.add(cached.getListenerList()
							.addRefsChangedListener(refsChangedListener));
					submodules.add(cached);
					submodule.close();
				}
			}
		} catch (IOException ex) {
			Activator.logError(MessageFormat.format(
					CoreText.IndexDiffCacheEntry_errorCalculatingIndexDelta,
					repository), ex);
		}
