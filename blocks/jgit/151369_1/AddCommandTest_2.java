			try (Git git = new Git(nestedRepo)) {
				git.add().addFilepattern(".").call();
				git.commit().setMessage("subrepo commit").call();
			} catch (GitAPIException e) {
				throw new RuntimeException(e);
			}
