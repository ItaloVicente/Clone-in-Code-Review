					ru.setExpectedOldObjectId(headId);
					Result rc = ru.update();
					switch (rc) {
					case NEW:
					case FAST_FORWARD: {
						setCallable(false);
						File meta = repo.getDirectory();
						if (state == RepositoryState.MERGING_RESOLVED
								&& meta != null) {
							new File(meta, Constants.MERGE_HEAD).delete();
							new File(meta, Constants.MERGE_MSG).delete();
