				if (isPruned())
					return NO_CHILDREN;
			case FAST_FORWARD:
				try (RevWalk walk = new RevWalk(reader)) {
					walk.setRetainBody(true);
					walk.markStart(walk.parseCommit(update.getNewObjectId()));
					walk.markUninteresting(walk.parseCommit(update
							.getOldObjectId()));
					List<RepositoryCommit> commits = new ArrayList<>();
					for (RevCommit commit : walk)
						commits.add(new RepositoryCommit(repo, commit));
					children = commits.toArray();
					break;
				} catch (IOException e) {
					Activator.logError(
							"Error parsing commits from fetch result", e); //$NON-NLS-1$
