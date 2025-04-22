			if (!event.getModified().isEmpty()
					|| !event.getDeleted().isEmpty()) {
				Set<String> toRefresh = new HashSet<>(event.getModified());
				toRefresh.addAll(event.getDeleted());
				refreshIndex(event.getRepository(), toRefresh);
			}
