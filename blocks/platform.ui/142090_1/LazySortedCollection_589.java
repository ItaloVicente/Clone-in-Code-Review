	public final void retainFirst(int n) {
		try {
			retainFirst(n, new FastProgressReporter());
		} catch (InterruptedException e) {
		}

		testInvariants();
	}

	public final void removeRange(int first, int length) {
		try {
			removeRange(first, length, new FastProgressReporter());
		} catch (InterruptedException e) {
		}

		testInvariants();
	}

		removeRange(root, first, length, mon);

		pack();

		testInvariants();
	}

	private final void removeRange(int node, int rangeStart, int rangeLength, FastProgressReporter mon) throws InterruptedException {
		if (rangeLength == 0) {
			return;
		}

		int size = getSubtreeSize(node);

		if (size <= rangeStart) {
			return;
		}

		if (rangeStart == 0 && rangeLength >= size) {
			removeSubTree(node);
			return;
		}
		try {
			node = partition(node, mon);

			int left = leftSubTree[node];
			int leftSize = getSubtreeSize(left);

			int toRemoveFromLeft = Math.min(leftSize - rangeStart, rangeLength);

			if (toRemoveFromLeft >= 0) {
				removeRange(leftSubTree[node], rangeStart, toRemoveFromLeft, mon);

				int toRemoveFromRight = rangeStart + rangeLength - leftSize - 1;

				if (toRemoveFromRight >= 0) {
					removeRange(rightSubTree[node], 0, toRemoveFromRight, mon);

					removeNode(node);
					return;
				}
			} else {
				removeRange(rightSubTree[node], rangeStart - leftSize - 1, rangeLength, mon);
			}
		} finally {
			recomputeTreeSize(node);
		}
	}

	private final void removeSubTree(int subTree) {
		if (subTree == -1) {
			return;
		}

		for (int next = nextUnsorted[subTree]; next != -1;) {
			int current = next;
			next = nextUnsorted[next];

			destroyNode(current);
		}

		removeSubTree(leftSubTree[subTree]);

		removeSubTree(rightSubTree[subTree]);

		replaceNode(subTree, -1);
		destroyNode(subTree);
	}

	private final int lazyRemoveNode(int subTree) {
		int left = leftSubTree[subTree];
		int right = rightSubTree[subTree];

		if (left == -1 && right == -1) {
			int result = nextUnsorted[subTree];
			replaceNode(subTree, result);
			destroyNode(subTree);
			return result;
		}

		Object value = contents[subTree];
		contents[subTree] = lazyRemovalFlag;
		treeSize[subTree]--;
		if (objectIndices != null) {
			objectIndices.remove(value);
		}

		return subTree;
	}

	private final int removeNode(int subTree) {
		int left = leftSubTree[subTree];
		int right = rightSubTree[subTree];

		if (left == -1 || right == -1) {
			int result = -1;

			if (left == -1 && right == -1) {
				result = nextUnsorted[subTree];
			} else {
				if (left == -1) {
					result = right;
				} else {
					result = left;
				}

				try {
					result = partition(result, new FastProgressReporter());
				} catch (InterruptedException e) {

				}
				if (result == -1) {
					result = nextUnsorted[subTree];
				} else {
					int unsorted = nextUnsorted[subTree];
					nextUnsorted[result] = unsorted;
					int additionalNodes = 0;
					if (unsorted != -1) {
						parentTree[unsorted] = result;
						additionalNodes = treeSize[unsorted];
					}
					treeSize[result] += additionalNodes;
				}
			}

			replaceNode(subTree, result);
			destroyNode(subTree);
			return result;
		}

		Edge nextSmallest = new Edge(subTree, DIR_LEFT);
		while (!nextSmallest.isNull()) {
			nextSmallest.advance(DIR_RIGHT);
		}

		Edge nextLargest = new Edge(subTree, DIR_RIGHT);
		while (!nextLargest.isNull()) {
			nextLargest.advance(DIR_LEFT);
		}

		int replacementNode = -1;


		int leftSize = getSubtreeSize(left);
		int rightSize = getSubtreeSize(right);

		if (leftSize > rightSize) {
			replacementNode = nextSmallest.getStart();

			Edge unsorted = new Edge(replacementNode, DIR_UNSORTED);
			while (!unsorted.isNull()) {
				int target = unsorted.getTarget();

				if (!isLess(target, replacementNode)) {
					unsorted.setTarget(nextUnsorted[target]);
					nextLargest.setTarget(addUnsorted(nextLargest.getTarget(), target));
				} else {
					unsorted.advance(DIR_UNSORTED);
				}
			}

			forceRecomputeTreeSize(unsorted.getStart(), replacementNode);
			forceRecomputeTreeSize(nextLargest.getStart(), subTree);
		} else {
			replacementNode = nextLargest.getStart();

			Edge unsorted = new Edge(replacementNode, DIR_UNSORTED);
			while (!unsorted.isNull()) {
				int target = unsorted.getTarget();

				if (isLess(target, replacementNode)) {
					unsorted.setTarget(nextUnsorted[target]);
					nextSmallest.setTarget(addUnsorted(nextSmallest.getTarget(), target));
				} else {
					unsorted.advance(DIR_UNSORTED);
				}
			}

			forceRecomputeTreeSize(unsorted.getStart(), replacementNode);
			forceRecomputeTreeSize(nextSmallest.getStart(), subTree);
		}

		Object replacementContent = contents[replacementNode];
		contents[replacementNode] = contents[subTree];
		contents[subTree] = replacementContent;

		if (objectIndices != null) {
			objectIndices.put(replacementContent, subTree);

		}

		int replacementParent = parentTree[replacementNode];

		replaceNode(replacementNode, removeNode(replacementNode));

		forceRecomputeTreeSize(replacementParent, subTree);
		recomputeTreeSize(subTree);


		return subTree;
	}

	public final void clear() {
		lastNode = 0;
		setArraySize(MIN_CAPACITY);
		root = -1;
		firstUnusedNode = -1;
		objectIndices = null;

		testInvariants();
	}

	public Comparator getComparator() {
		return comparator;
	}

		int returnValue = getRange(result, 0, sorted, mon);

		testInvariants();

		return returnValue;
	}

	public final int getFirst(Object[] result, boolean sorted) {
		int returnValue = 0;

		try {
			returnValue = getFirst(result, sorted, new FastProgressReporter());
		} catch (InterruptedException e) {
		}

		testInvariants();

		return returnValue;
	}

		return getRange(result, 0, rangeStart, root, sorted, mon);
	}

	public final int getRange(Object[] result, int rangeStart, boolean sorted) {
		int returnValue = 0;

		try {
			returnValue = getRange(result, rangeStart, sorted, new FastProgressReporter());
		} catch (InterruptedException e) {
		}

		testInvariants();

		return returnValue;
	}

	public final Object getItem(int index) {
		Object[] result = new Object[1];
		try {
			getRange(result, index, false, new FastProgressReporter());
		} catch (InterruptedException e) {
		}
		Object returnValue = result[0];

		testInvariants();

		return returnValue;
	}

	public final Object[] getItems(boolean sorted) {
		Object[] result = new Object[size()];

		getRange(result, 0, sorted);

		return result;
	}

	private final int getRange(Object[] result, int resultIdx, int rangeStart, int node, boolean sorted, FastProgressReporter mon) throws InterruptedException {
		if (node == -1) {
			return 0;
		}

		int availableSpace = result.length - resultIdx;

		if (rangeStart == 0) {
			if (treeSize[node] <= availableSpace) {
				return getChildren(result, resultIdx, node, sorted, mon);
			}
		}

		node = partition(node, mon);
		if (node == -1) {
			return 0;
		}

		int inserted = 0;

		int numberLessThanNode = getSubtreeSize(leftSubTree[node]);

		if (rangeStart < numberLessThanNode) {
			if (inserted < availableSpace) {
				inserted += getRange(result, resultIdx, rangeStart, leftSubTree[node], sorted, mon);
			}
		}

		if (rangeStart <= numberLessThanNode) {
			if (inserted < availableSpace) {
				result[resultIdx + inserted] = contents[node];
				inserted++;
			}
		}

		if (inserted < availableSpace) {
			inserted += getRange(result, resultIdx + inserted,
				Math.max(rangeStart - numberLessThanNode - 1, 0), rightSubTree[node], sorted, mon);
		}

		return inserted;
	}

	private final int getChildren(Object[] result, int resultIdx, int node, boolean sorted, FastProgressReporter mon) throws InterruptedException {
		if (node == -1) {
			return 0;
		}

		int tempIdx = resultIdx;

		if (sorted) {
			node = partition(node, mon);
			if (node == -1) {
				return 0;
			}
		}

		if (tempIdx < result.length) {
			tempIdx += getChildren(result, tempIdx, leftSubTree[node], sorted, mon);
		}

		if (tempIdx < result.length) {
			Object value = contents[node];
			if (value != lazyRemovalFlag) {
				result[tempIdx++] = value;
			}
		}

		if (tempIdx < result.length) {
			tempIdx += getChildren(result, tempIdx, rightSubTree[node], sorted, mon);
		}

		for (int unsortedNode = nextUnsorted[node]; unsortedNode != -1 && tempIdx < result.length;
			unsortedNode = nextUnsorted[unsortedNode]) {

			result[tempIdx++] = contents[unsortedNode];
		}

		return tempIdx - resultIdx;
	}

	public boolean contains(Object item) {
		Assert.isNotNull(item);
		boolean returnValue = (getObjectIndex(item) != -1);

		testInvariants();

		return returnValue;
	}
