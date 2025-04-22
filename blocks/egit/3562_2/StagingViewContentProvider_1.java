			if (params.length == 3) {
				nodes.addAll(Arrays.asList(content));
				Collection<String> removedResources = (Collection<String>)params[2];
				for (String res : removedResources)
					for (StagingEntry entry : content)
						if (entry.getPath().equals(res))
							nodes.remove(entry);
			}

