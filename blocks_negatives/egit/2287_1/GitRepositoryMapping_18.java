		for (GitModelObject obj : gitRepo.getChildren())
			if (obj instanceof GitModelCommit || obj instanceof GitModelCache) {
				RevCommit revCommit;
				if (obj instanceof GitModelCommit)
					revCommit = ((GitModelCommit) obj).getBaseCommit();
				else
					revCommit = ((GitModelCache) obj).getBaseCommit();

				result.add(new GitTreeTraversal(repo, revCommit));
			}

		return result.toArray(new ResourceTraversal[result.size()]);
