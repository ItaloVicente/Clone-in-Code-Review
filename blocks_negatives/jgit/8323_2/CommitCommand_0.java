					dcEditor.add(new PathEdit(path) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.copyMetaData(dcEntry);
						}
					});
