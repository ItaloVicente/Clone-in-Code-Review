package org.eclipse.egit.ui.internal.repository.tree.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jgit.revwalk.RevCommit;

public class NodesByCommitTimeFilter {

	private static final Pattern FILTER_ACTIVE_PATTERN = Pattern
			.compile("#\\d*"); //$NON-NLS-1$

	private boolean active;

	private int maxCount = -1;

	private int thresholdTime = Integer.MIN_VALUE;

	List<TimedNode> nodes = new ArrayList<>();

	List<RepositoryTreeNode<?>> allNodes = new ArrayList<>();

	public NodesByCommitTimeFilter(String filterText) {
		if (filterText != null) {
			Matcher matcher = FILTER_ACTIVE_PATTERN.matcher(filterText);
			active = matcher.matches();
			if (active) {
				if (filterText.length() > 1) {
					try {
						maxCount = Integer.parseInt(filterText.substring(1));
					} catch (NumberFormatException e) {
					}
				}
			}
		}
	}

	public void addNode(RepositoryTreeNode<?> treeNode, Object timeCarrier) {
		if (isFiltering()) {
			int time = Integer.MIN_VALUE;
			if (timeCarrier instanceof RevCommit) {
				time = ((RevCommit) timeCarrier).getCommitTime();
			}
			if (time >= thresholdTime) {
				TimedNode node = new TimedNode(treeNode, time);
				nodes.add(node);
				int size = nodes.size();
				if (size > maxCount) {
					Collections.sort(nodes);
					nodes = nodes.subList(size - maxCount, size);
					thresholdTime = nodes.get(0).time;
				}
			}
		} else {
			allNodes.add(treeNode);
		}
	}

	public List<RepositoryTreeNode<?>> getFilteredNodes() {
		if (isFiltering()) {
			return nodes.stream().map(node -> node.node)
					.collect(Collectors.toList());
		} else {
			return allNodes;
		}
	}

	public boolean isFilterActive() {
		return active;
	}

	private boolean isFiltering() {
		return maxCount > 0; // implies active==true
	}

	private static class TimedNode implements Comparable<TimedNode> {

		RepositoryTreeNode<?> node;

		int time;

		TimedNode(RepositoryTreeNode<?> node, int time) {
			this.node = node;
			this.time = time;
		}

		@Override
		public int compareTo(TimedNode o) {
			return Integer.compare(time, o.time);
		}
	}
}
