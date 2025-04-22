				if (parentIndex >= 0
						&& parentIndex < commit.getParentCount()) {
					command.setMainlineParentNumber(parentIndex + 1);
				}
				result = command.call();
			} catch (GitAPIException e) {
				throw new TeamException(e.getLocalizedMessage(),
						e.getCause());
