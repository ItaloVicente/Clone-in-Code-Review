			dc = repository.lockDirCache();
			dc.clear();
			DirCacheBuilder dcb = dc.builder();
			dcb.addTree(new byte[0], 0, repository.newObjectReader(), commit
					.getTree());
			dcb.commit();
