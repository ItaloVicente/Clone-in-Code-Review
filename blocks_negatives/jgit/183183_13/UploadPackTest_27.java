				@Override
				void addElements(DirCacheBuilder dcBuilder) throws Exception {
					dcBuilder.add(remote.file("1", blobLowDepth));
					dcBuilder.addTree(new byte[] {'2'}, DirCacheEntry.STAGE_0,
							remote.getRevWalk().getObjectReader(), subtree);
				}
			}).build();
