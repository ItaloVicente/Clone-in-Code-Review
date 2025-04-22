				ObjectId id = ref.getObjectId();
				if (id == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().cannotUpdateUnbornBranch);
					continue;
				}
