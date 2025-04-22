				editor.add(new PathEdit(entry) {

					@Override
					public void apply(DirCacheEntry ent) {
						ent.copyMetaData(entry);
					}
				});
