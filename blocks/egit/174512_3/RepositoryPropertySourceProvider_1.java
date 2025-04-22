			update |= mgr.remove(
					RepositoryPropertySource.CHANGEMODEACTIONID) != null;
			update |= mgr.remove(RepositoryPropertySource.SINGLEVALUEACTIONID) != null;
			update |= mgr.remove(RepositoryPropertySource.EDITACTIONID) != null;
			update |= mgr.remove(BranchPropertySource.EDITACTIONID) != null;
			if (update) {
				bars.updateActionBars();
			}
