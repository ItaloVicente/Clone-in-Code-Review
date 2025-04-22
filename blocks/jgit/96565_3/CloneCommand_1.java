		} catch (GitAPIException | RuntimeException e) {
			if (repository != null) {
				repository.close();
			}
			cleanup();
			throw e;
