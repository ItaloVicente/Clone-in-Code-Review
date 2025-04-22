				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} catch (IOException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
