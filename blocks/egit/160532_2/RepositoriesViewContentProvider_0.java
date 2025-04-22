	private void addTagNode(Map<Integer, List<TagNode>> timedNodes,
			TagNode tagNode, RevObject peeledObject) {
		int time=Integer.MIN_VALUE;
		if (peeledObject instanceof RevCommit) {
			time = ((RevCommit) peeledObject).getCommitTime();
		}
		List<TagNode> nodes = timedNodes.computeIfAbsent(Integer.valueOf(time),
				i -> new ArrayList<>());
		nodes.add(tagNode);

	}

	private Collection<TagNode> getTimeFilteredNodes(
			Map<Integer, List<TagNode>> timedNodes,
			String filterText) {
		if (filterText != null && filterText.matches("#\\d+")) { //$NON-NLS-1$
			int count = Integer
					.parseInt(filterText.substring(1));
			return timedNodes.keySet().stream()
					.sorted(Collections.reverseOrder())
					.flatMap(i -> timedNodes.get(i).stream()).limit(count)
					.collect(Collectors.toList());
		} else {
			return timedNodes.values().stream().flatMap(l -> l.stream())
					.collect(Collectors.toList());
		}
	}

