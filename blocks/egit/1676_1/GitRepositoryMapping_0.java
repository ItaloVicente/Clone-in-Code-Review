			if (obj instanceof GitModelCommit || obj instanceof GitModelCache) {
				RevCommit revCommit;
				if (obj instanceof GitModelCommit)
					revCommit = ((GitModelCommit) obj).getRemoteCommit();
				else
					revCommit = ((GitModelCache) obj).getRemoteCommit();

