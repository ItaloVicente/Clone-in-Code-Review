				command.call();
				progress.worked(1);
				ProjectUtil.refreshValidProjects(validProjects,
						progress.newChild(1));
			} catch (JGitInternalException | GitAPIException e) {
				throw new TeamException(e.getLocalizedMessage(),
						e.getCause());
