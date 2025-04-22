			if (org.eclipse.egit.core.Activator.autoStageMoves()) {
				final DirCacheEditor sEdit = sCache.editor();
				sEdit.add(new DirCacheEditor.DeletePath(sEnt));
				if (dstm != null
						&& dstm.getRepository() == srcm.getRepository()) {
					final String dPath = srcm.getRepoRelativePath(dstf);
					sEdit.add(new DirCacheEditor.PathEdit(dPath) {

						@Override
						public void apply(final DirCacheEntry dEnt) {
							dEnt.copyMetaData(sEnt);
						}
					});
				}
				if (!sEdit.commit()) {
					tree.failed(new Status(IStatus.ERROR,
							Activator.getPluginId(), 0,
							CoreText.MoveDeleteHook_operationError, null));
				}
