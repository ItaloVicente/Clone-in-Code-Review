		private IDiff[] getSetToMerge(IMergeContext mergeContext) {
			ResourceMapping[] mappings = mergeContext.getScope().getMappings(
					providerID);
			Set<IDiff> deltas = new HashSet<IDiff>();
			for (int i = 0; i < mappings.length; i++) {
				ResourceTraversal[] traversals = mergeContext.getScope()
						.getTraversals(mappings[i]);
				deltas.addAll(Arrays.asList(mergeContext.getDiffTree()
						.getDiffs(traversals)));
			}
			return deltas.toArray(new IDiff[deltas.size()]);
		}
