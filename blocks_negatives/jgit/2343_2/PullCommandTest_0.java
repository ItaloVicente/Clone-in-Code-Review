		RevCommit commit = source.commit().setMessage(
				"Initial commit for source").call();

		RefUpdate upd = dbTarget.updateRef("refs/heads/master");
		upd.setNewObjectId(commit.getId());
		upd.update();
