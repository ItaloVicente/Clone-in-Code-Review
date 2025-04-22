				final CompletableFuture<Map<IResource, Integer>> severitiesSnapshot = this.severities;
				if (severitiesSnapshot != null) {
					Integer severity = severitiesSnapshot.get(50000000, TimeUnit.MILLISECONDS).get(resource);
					if (severity != null) {
						problemSeverity = Math.max(problemSeverity, severity.intValue());
					}
