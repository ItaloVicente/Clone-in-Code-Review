			} catch (GitAPIException | IOException e) {
				throw new TeamException(e.getLocalizedMessage(),
						e.getCause());
			} finally {
				if (updated != null && !updated.isEmpty()) {
					repository.notifyIndexChanged(true);
				}
