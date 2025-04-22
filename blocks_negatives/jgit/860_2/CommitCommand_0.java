				ru.setExpectedOldObjectId(headId);
				Result rc = ru.update();
				switch (rc) {
				case NEW:
				case FAST_FORWARD:
					setCallable(false);
					if (state == RepositoryState.MERGING_RESOLVED) {
						new File(repo.getDirectory(), Constants.MERGE_HEAD)
								.delete();
						new File(repo.getDirectory(), Constants.MERGE_MSG)
								.delete();
