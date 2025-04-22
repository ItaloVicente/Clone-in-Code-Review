			return "Lazy removal flag";  //$NON-NLS-1$
		}
	};

	private static final int DIR_LEFT = 0;
	private static final int DIR_RIGHT = 1;
	private static final int DIR_UNSORTED = 2;

	private static final int DIR_ROOT = 3;
	private static final int DIR_UNUSED = 4;

	private final class Edge {
		private int startNode;
		private int direction;

		private Edge() {
			startNode = -1;
			direction = -1;
		}

		private Edge(int node, int dir) {
			startNode = node;
			direction = dir;
		}

		private int getStart() {
			return startNode;
		}

		private int getTarget() {
			if (startNode == -1) {
				if (direction == DIR_UNSORTED) {
					return firstUnusedNode;
				} else if (direction == DIR_ROOT) {
					return root;
				}
				return -1;
			}

			if (direction == DIR_LEFT) {
				return leftSubTree[startNode];
			}
			if (direction == DIR_RIGHT) {
				return rightSubTree[startNode];
			}
			return nextUnsorted[startNode];
		}

		private boolean isNull() {
			return getTarget() == -1;
		}

		private void setTarget(int newNode) {
			if (direction == DIR_LEFT) {
				leftSubTree[startNode] = newNode;
			} else if (direction == DIR_RIGHT) {
				rightSubTree[startNode] = newNode;
			} else if (direction == DIR_UNSORTED) {
				nextUnsorted[startNode] = newNode;
			} else if (direction == DIR_ROOT) {
				root = newNode;
			} else if (direction == DIR_UNUSED) {
				firstUnusedNode = newNode;
			}

			if (newNode != -1) {
				parentTree[newNode] = startNode;
			}
		}

		private void advance(int direction) {
			startNode = getTarget();
			this.direction = direction;
		}
	}

	private void setRootNode(int node) {
		root = node;
		if (node != -1) {
			parentTree[node] = -1;
		}
	}

	public LazySortedCollection(Comparator c) {
		this.comparator = c;
	}

	public void testInvariants() {
		if (!enableDebug) {
			return;
		}

		testInvariants(root);
	}

	private void testInvariants(int node) {
		if (node == -1) {
			return;
		}

		int treeSize = getSubtreeSize(node);

		int left = leftSubTree[node];
		int right = rightSubTree[node];
		int unsorted = nextUnsorted[node];

		if (isUnsorted(node)) {
			Assert.isTrue(left == -1, "unsorted nodes shouldn't have a left subtree"); //$NON-NLS-1$
			Assert.isTrue(right == -1, "unsorted nodes shouldn't have a right subtree"); //$NON-NLS-1$
		}

		if (left != -1) {
			testInvariants(left);
			Assert.isTrue(parentTree[left] == node, "left node has invalid parent pointer"); //$NON-NLS-1$
		}
		if (right != -1) {
			testInvariants(right);
			Assert.isTrue(parentTree[right] == node, "right node has invalid parent pointer");             //$NON-NLS-1$
		}

		int previous = node;
		while (unsorted != -1) {
			int oldTreeSize = this.treeSize[unsorted];
			recomputeTreeSize(unsorted);

			Assert.isTrue(this.treeSize[unsorted] == oldTreeSize,
					"Invalid node size for unsorted node"); //$NON-NLS-1$
			Assert.isTrue(leftSubTree[unsorted] == -1, "unsorted nodes shouldn't have left subtrees"); //$NON-NLS-1$
			Assert.isTrue(rightSubTree[unsorted] == -1, "unsorted nodes shouldn't have right subtrees"); //$NON-NLS-1$
			Assert.isTrue(parentTree[unsorted] == previous, "unsorted node has invalid parent pointer"); //$NON-NLS-1$
			Assert.isTrue(contents[unsorted] != lazyRemovalFlag, "unsorted nodes should not be lazily removed"); //$NON-NLS-1$
			previous = unsorted;
			unsorted = nextUnsorted[unsorted];
		}

		recomputeTreeSize(node);

		Assert.isTrue(treeSize == getSubtreeSize(node), "invalid tree size"); //$NON-NLS-1$
	}

	private boolean isUnsorted(int node) {
		int parent = parentTree[node];

		if (parent != -1) {
			return nextUnsorted[parent] == node;
		}

		return false;
	}

	private final boolean isLess(int element1, int element2) {
		return comparator.compare(contents[element1], contents[element2]) < 0;
	}

	private final int addUnsorted(int subTree, int elementToAdd) {
		if (elementToAdd == -1) {
			return subTree;
		}

		if (subTree == -1) {
			nextUnsorted[elementToAdd] = -1;
			treeSize[elementToAdd] = 1;
			return elementToAdd;
		}

		if (treeSize[subTree] == 0) {
			removeSubTree(subTree);
			nextUnsorted[elementToAdd] = -1;
			treeSize[elementToAdd] = 1;
			return elementToAdd;
		}

		if (!enableDebug && leftSubTree[subTree] == -1 && rightSubTree[subTree] == -1
				&& leftSubTree[elementToAdd] == -1 && rightSubTree[elementToAdd] == -1) {
			counter--;

			if (counter % treeSize[subTree] == 0) {
				nextUnsorted[elementToAdd] = subTree;
				parentTree[elementToAdd] = parentTree[subTree];
				parentTree[subTree] = elementToAdd;
				treeSize[elementToAdd] = treeSize[subTree] + 1;
				return elementToAdd;
			}
		}

		int oldNextUnsorted = nextUnsorted[subTree];
		nextUnsorted[elementToAdd] = oldNextUnsorted;

		if (oldNextUnsorted == -1) {
			treeSize[elementToAdd] = 1;
		} else {
			treeSize[elementToAdd] = treeSize[oldNextUnsorted] + 1;
			parentTree[oldNextUnsorted] = elementToAdd;
		}

		parentTree[elementToAdd] = subTree;

		nextUnsorted[subTree] = elementToAdd;
		treeSize[subTree]++;
		return subTree;
	}

	public int size() {
		int result = getSubtreeSize(root);

		testInvariants();

		return result;
	}

	private final int partition(int subTree, int toMove) {
		int result = nextUnsorted[toMove];

		if (isLess(toMove, subTree)) {
			int nextLeft = addUnsorted(leftSubTree[subTree], toMove);
			leftSubTree[subTree] = nextLeft;
			parentTree[nextLeft] = subTree;
		} else {
			int nextRight = addUnsorted(rightSubTree[subTree], toMove);
			rightSubTree[subTree] = nextRight;
			parentTree[nextRight] = subTree;
		}

		return result;
	}

	private final int partition(int subTree, FastProgressReporter mon) throws InterruptedException {
		if (subTree == -1) {
			return -1;
		}

		if (contents[subTree] == lazyRemovalFlag) {
			subTree = removeNode(subTree);
			if (subTree == -1) {
				return -1;
			}
		}

		for (int idx = nextUnsorted[subTree]; idx != -1;) {
			idx = partition(subTree, idx);
			nextUnsorted[subTree] = idx;
			if (idx != -1) {
				parentTree[idx] = subTree;
			}

			if (mon.isCanceled()) {
				throw new InterruptedException();
			}
		}

		nextUnsorted[subTree] = -1;

		return subTree;
	}

	private final int getSubtreeSize(int subTree) {
		if (subTree == -1) {
			return 0;
		}
		return treeSize[subTree];
	}

	public final void setCapacity(int newSize) {
		if (newSize > contents.length) {
			setArraySize(newSize);
		}
	}

	private final void setArraySize(int newCapacity) {
		Object[] newContents = new Object[newCapacity];
		System.arraycopy(contents, 0, newContents, 0, lastNode);
		contents = newContents;

		int[] newLeftSubTree = new int[newCapacity];
		System.arraycopy(leftSubTree, 0, newLeftSubTree, 0, lastNode);
		leftSubTree = newLeftSubTree;

		int[] newRightSubTree = new int[newCapacity];
		System.arraycopy(rightSubTree, 0, newRightSubTree, 0, lastNode);
		rightSubTree = newRightSubTree;

		int[] newNextUnsorted = new int[newCapacity];
		System.arraycopy(nextUnsorted, 0, newNextUnsorted, 0, lastNode);
		nextUnsorted = newNextUnsorted;

		int[] newTreeSize = new int[newCapacity];
		System.arraycopy(treeSize, 0, newTreeSize, 0, lastNode);
		treeSize = newTreeSize;

		int[] newParentTree = new int[newCapacity];
		System.arraycopy(parentTree, 0, newParentTree, 0, lastNode);
		parentTree = newParentTree;
	}

	private final int createNode(Object value) {
		int result = -1;

		if (firstUnusedNode == -1) {
			result = lastNode;

			if (contents.length <= lastNode) {
				setCapacity(lastNode * 2);
			}

			lastNode++;
		} else {
			result = firstUnusedNode;
			firstUnusedNode = nextUnsorted[result];
		}

		contents[result] = value;
		treeSize[result] = 1;

		leftSubTree[result] = -1;
		rightSubTree[result] = -1;
		nextUnsorted[result] = -1;

		if (objectIndices != null) {
			objectIndices.put(value, result);
		}

		return result;
	}

	private int getObjectIndex(Object value) {
		if (objectIndices == null) {
			int result = -1;

			objectIndices = new IntHashMap((int)(contents.length / loadFactor) + 1, loadFactor);

			for (int i = 0; i < lastNode; i++) {
				Object element = contents[i];

				if (element != null && element != lazyRemovalFlag) {
					objectIndices.put(element, i);

					if (value == element) {
						result = i;
					}
				}
			}

			return result;
		}

		return objectIndices.get(value, -1);
	}

	private void replaceNode(int nodeToReplace, int replacementNode) {
		int parent = parentTree[nodeToReplace];

		if (parent == -1) {
			if (root == nodeToReplace) {
				setRootNode(replacementNode);
			}
		} else {
			if (leftSubTree[parent] == nodeToReplace) {
				leftSubTree[parent] = replacementNode;
			} else if (rightSubTree[parent] == nodeToReplace) {
				rightSubTree[parent] = replacementNode;
			} else if (nextUnsorted[parent] == nodeToReplace) {
				nextUnsorted[parent] = replacementNode;
			}
			if (replacementNode != -1) {
				parentTree[replacementNode] = parent;
			}
		}
	}

	private void recomputeAncestorTreeSizes(int node) {
		while (node != -1) {
			int oldSize = treeSize[node];

			recomputeTreeSize(node);

			if (treeSize[node] == oldSize) {
				break;
			}

			node = parentTree[node];
		}
	}

	private void recomputeTreeSize(int node) {
		if (node == -1) {
			return;
		}
		treeSize[node] = getSubtreeSize(leftSubTree[node])
			+ getSubtreeSize(rightSubTree[node])
			+ getSubtreeSize(nextUnsorted[node])
			+ (contents[node] == lazyRemovalFlag ? 0 : 1);
	}

	private void forceRecomputeTreeSize(int toRecompute, int whereToStop) {
		while (toRecompute != -1 && toRecompute != whereToStop) {
			recomputeTreeSize(toRecompute);

			toRecompute = parentTree[toRecompute];
		}
	}

	private void destroyNode(int nodeToDestroy) {
		if (objectIndices != null) {
			Object oldContents = contents[nodeToDestroy];
			if (oldContents != lazyRemovalFlag) {
				objectIndices.remove(oldContents);
			}
		}

		contents[nodeToDestroy] = null;
		leftSubTree[nodeToDestroy] = -1;
		rightSubTree[nodeToDestroy] = -1;

		if (firstUnusedNode == -1) {
			treeSize[nodeToDestroy] = 1;
		} else {
			treeSize[nodeToDestroy] = treeSize[firstUnusedNode] + 1;
			parentTree[firstUnusedNode] = nodeToDestroy;
		}

		nextUnsorted[nodeToDestroy] = firstUnusedNode;

		firstUnusedNode = nodeToDestroy;
	}

	private final void pack() {

		if (firstUnusedNode == -1) {
			return;
		}

		int reusableNodes = getSubtreeSize(firstUnusedNode);
		int nonPackableNodes = lastNode - reusableNodes;

		if (contents.length < MIN_CAPACITY || nonPackableNodes > contents.length / 4) {
			return;
		}

		objectIndices = null;

		int[] mapNewIdxOntoOld = new int[contents.length];
		int[] mapOldIdxOntoNew = new int[contents.length];

		int nextNewIdx = 0;
		for (int oldIdx = 0; oldIdx < lastNode; oldIdx++) {
			if (contents[oldIdx] != null) {
				mapOldIdxOntoNew[oldIdx] = nextNewIdx;
				mapNewIdxOntoOld[nextNewIdx] = oldIdx;
				nextNewIdx++;
			} else {
				mapOldIdxOntoNew[oldIdx] = -1;
			}
		}

		int newNodes = nextNewIdx;
		int newCapacity = Math.max(newNodes * 2, MIN_CAPACITY);

		Object[] newContents = new Object[newCapacity];
		int[] newTreeSize = new int[newCapacity];
		int[] newNextUnsorted = new int[newCapacity];
		int[] newLeftSubTree = new int[newCapacity];
		int[] newRightSubTree = new int[newCapacity];
		int[] newParentTree = new int[newCapacity];

		for (int newIdx = 0; newIdx < newNodes; newIdx++) {
			int oldIdx = mapNewIdxOntoOld[newIdx];
			newContents[newIdx] = contents[oldIdx];
			newTreeSize[newIdx] = treeSize[oldIdx];

			int left = leftSubTree[oldIdx];
			if (left == -1) {
				newLeftSubTree[newIdx] = -1;
			} else {
				newLeftSubTree[newIdx] = mapOldIdxOntoNew[left];
			}

			int right = rightSubTree[oldIdx];
			if (right == -1) {
				newRightSubTree[newIdx] = -1;
			} else {
				newRightSubTree[newIdx] = mapOldIdxOntoNew[right];
			}

			int unsorted = nextUnsorted[oldIdx];
			if (unsorted == -1) {
				newNextUnsorted[newIdx] = -1;
			} else {
				newNextUnsorted[newIdx] = mapOldIdxOntoNew[unsorted];
			}

			int parent = parentTree[oldIdx];
			if (parent == -1) {
				newParentTree[newIdx] = -1;
			} else {
				newParentTree[newIdx] = mapOldIdxOntoNew[parent];
			}
		}

		contents = newContents;
		nextUnsorted = newNextUnsorted;
		treeSize = newTreeSize;
		leftSubTree = newLeftSubTree;
		rightSubTree = newRightSubTree;
		parentTree = newParentTree;

		if (root != -1) {
			root = mapOldIdxOntoNew[root];
		}

		firstUnusedNode = -1;
		lastNode = newNodes;
	}

	public final void add(Object toAdd) {
		Assert.isNotNull(toAdd);
		int newIdx = createNode(toAdd);

		setRootNode(addUnsorted(root, newIdx));

		testInvariants();
	}

	public final void addAll(Collection toAdd) {
		Assert.isNotNull(toAdd);
		Iterator iter = toAdd.iterator();
		while (iter.hasNext()) {
			add(iter.next());
		}

		testInvariants();
	}

	public final void addAll(Object[] toAdd) {
		Assert.isNotNull(toAdd);
		for (Object object : toAdd) {
			add(object);
		}

		testInvariants();
	}

	public final boolean isEmpty() {
		boolean result = (root == -1);

		testInvariants();

		return result;
	}

	public final void remove(Object toRemove) {
		internalRemove(toRemove);

		pack();

		testInvariants();
	}

	private void internalRemove(Object toRemove) {
		int objectIndex = getObjectIndex(toRemove);

		if (objectIndex != -1) {
			int parent = parentTree[objectIndex];
			lazyRemoveNode(objectIndex);
			recomputeAncestorTreeSizes(parent);
		}

	}

	public final void removeAll(Object[] toRemove) {
		Assert.isNotNull(toRemove);

		for (Object object : toRemove) {
			internalRemove(object);
		}
		pack();
	}

		int sz = size();

		if (n >= sz) {
			return;
		}

		removeRange(n, sz - n, mon);

		testInvariants();
	}

