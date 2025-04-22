			if (update.changedResources != null && update.changedResources.size() != 0) {
				nodes.addAll(Arrays.asList(content));
				for (String res : update.changedResources)
					for (StagingEntry entry : content)
						if (entry.getPath().equals(res))
							nodes.remove(entry);
			}

			final IndexDiff indexDiff = update.indexDiff;
			final Repository repository = update.repository;
