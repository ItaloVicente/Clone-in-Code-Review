			try {
				Tag t = repository.mapTag(refName, commitId);
				commit = repository.mapCommit(t.getObjId());
			} catch (IOException e2) {
				throw new TeamException(NLS.bind(
						CoreText.ResetOperation_lookingUpCommit, commitId), e2);
			}
