			Ref ref = repo.getRef(name);
			Result updateResult;
			if (ref != null)
				updateResult = refUpdate.link(ref.getName());
			else {
				refUpdate.setNewObjectId(newCommit);
				updateResult = refUpdate.forceUpdate();
			}
