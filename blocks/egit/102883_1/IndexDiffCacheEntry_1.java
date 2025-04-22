					submodules.put(cached, walk.getPath());
					IndexDiffCacheEntry submoduleCache = org.eclipse.egit.core.Activator
							.getDefault().getIndexDiffCache()
							.getIndexDiffCacheEntry(cached);
					if (submoduleCache != null) {
						submoduleCache
								.addIndexDiffChangedListener(submoduleListener);
					}
