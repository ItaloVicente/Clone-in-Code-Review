			if (update.changedResources != null
					&& !update.changedResources.isEmpty()) {
				nodes.addAll(Arrays.asList(content));
				for (String res : update.changedResources)
					for (StagingEntry entry : content)
						if (entry.getPath().equals(res))
							nodes.remove(entry);
			}
