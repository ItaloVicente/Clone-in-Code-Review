			RevWalk walk = new org.eclipse.jgit.revwalk.RevWalk(input.getRepository());
			try {
				return walk.parseCommit(commit.getId());
			} catch (IOException e) {
				throw new RuntimeException(
						"Could not parse commit " + commit.getId(), e); //$NON-NLS-1$
			} finally {
				walk.release();
			}
