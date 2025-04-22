			update = update
					| mgr.remove(RepositoryPropertySource.CHANGEMODEACTIONID) != null;
			update = update
					| mgr.remove(RepositoryPropertySource.SINGLEVALUEACTIONID) != null;
			update = update
					| mgr.remove(RepositoryPropertySource.EDITACTIONID) != null;
			update = update
					| mgr.remove(BranchPropertySource.EDITACTIONID) != null;
			if (update)
				mgr.update(false);
