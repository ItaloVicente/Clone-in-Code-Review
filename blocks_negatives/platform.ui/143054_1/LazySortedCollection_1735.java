        }
    };

    private final static int DIR_LEFT = 0;
    private final static int DIR_RIGHT = 1;
    private final static int DIR_UNSORTED = 2;

    private final static int DIR_ROOT = 3;
    private final static int DIR_UNUSED = 4;

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

        /**
         * Redirects this edge to a new node
         * @param newNode
         * @since 3.1
         */
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

    /**
     * Creates a new sorted collection using the given comparator to determine
     * sort order.
     *
     * @param c comparator that determines the sort order
     */
    public LazySortedCollection(Comparator c) {
        this.comparator = c;
    }

    /**
     * Tests if this object's internal state is valid. Throws a runtime
     * exception if the state is invalid, indicating a programming error
     * in this class. This method is intended for use in test
     * suites and should not be called by clients.
     */
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

    /**
     * Adds the given element to the given subtree. Returns the new
     * root of the subtree.
     *
     * @param subTree index of the subtree to insert elementToAdd into. If -1,
     *                then a new subtree will be created for elementToAdd
     * @param elementToAdd index of the element to add to the subtree. If -1, this method
     *                 is a NOP.
     * @since 3.1
     */
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

    /**
     * Returns the number of elements in the collection
     *
     * @return the number of elements in the collection
     */
    public int size() {
        int result = getSubtreeSize(root);

        testInvariants();

        return result;
    }

    /**
     * Given a tree and one of its unsorted children, this sorts the child by moving
     * it into the left or right subtrees. Returns the next unsorted child or -1 if none
     *
     * @param subTree parent tree
     * @param toMove child (unsorted) subtree
     * @since 3.1
     */
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

    /**
     * Partitions the given subtree. Moves all unsorted elements at the given node
     * to either the left or right subtrees. If the node itself was scheduled for
     * lazy removal, this will force the node to be removed immediately. Returns
     * the new subTree.
     *
     * @param subTree
     * @return the replacement node (this may be different from subTree if the subtree
     * was replaced during the removal)
     * @since 3.1
     */
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

    /**
     * Increases the capacity of this collection, if necessary, so that it can hold the
     * given number of elements. This can be used prior to a sequence of additions to
     * avoid memory reallocation. This cannot be used to reduce the amount
     * of memory used by the collection.
     *
     * @param newSize capacity for this collection
     */
    public final void setCapacity(int newSize) {
        if (newSize > contents.length) {
            setArraySize(newSize);
        }
    }

    /**
     * Adjusts the capacity of the array.
     *
     * @param newCapacity
     */
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

    /**
     * Creates a new node with the given value. Returns the index of the newly
     * created node.
     *
     * @param value
     * @return the index of the newly created node
     * @since 3.1
     */
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

    /**
     * Returns the current tree index for the given object.
     *
     * @param value
     * @return the current tree index
     * @since 3.1
     */
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

    /**
     * Redirects any pointers from the original to the replacement. If the replacement
     * causes a change in the number of elements in the parent tree, the changes are
     * propogated toward the root.
     *
     * @param nodeToReplace
     * @param replacementNode
     * @since 3.1
     */
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

    /**
     * Recomputes the tree size for the given node.
     *
     * @param node
     * @since 3.1
     */
    private void recomputeTreeSize(int node) {
        if (node == -1) {
            return;
        }
        treeSize[node] = getSubtreeSize(leftSubTree[node])
    		+ getSubtreeSize(rightSubTree[node])
    		+ getSubtreeSize(nextUnsorted[node])
    		+ (contents[node] == lazyRemovalFlag ? 0 : 1);
    }

    /**
     *
     * @param toRecompute
     * @param whereToStop
     * @since 3.1
     */
    private void forceRecomputeTreeSize(int toRecompute, int whereToStop) {
        while (toRecompute != -1 && toRecompute != whereToStop) {
	        recomputeTreeSize(toRecompute);

	        toRecompute = parentTree[toRecompute];
        }
    }

    /**
     * Destroy the node at the given index in the tree
     * @param nodeToDestroy
     * @since 3.1
     */
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

    /**
     * Frees up memory by clearing the list of nodes that have been freed up through removals.
     *
     * @since 3.1
     */
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

    /**
     * Adds the given object to the collection. Runs in O(1) amortized time.
     *
     * @param toAdd object to add
     */
    public final void add(Object toAdd) {
    	Assert.isNotNull(toAdd);
        int newIdx = createNode(toAdd);

        setRootNode(addUnsorted(root, newIdx));

        testInvariants();
    }

    /**
     * Adds all items from the given collection to this collection
     *
     * @param toAdd objects to add
     */
    public final void addAll(Collection toAdd) {
    	Assert.isNotNull(toAdd);
        Iterator iter = toAdd.iterator();
        while (iter.hasNext()) {
            add(iter.next());
        }

        testInvariants();
    }

    /**
     * Adds all items from the given array to the collection
     *
     * @param toAdd objects to add
     */
    public final void addAll(Object[] toAdd) {
    	Assert.isNotNull(toAdd);
        for (Object object : toAdd) {
            add(object);
        }

        testInvariants();
    }

    /**
     * Returns true iff the collection is empty
     *
     * @return true iff the collection contains no elements
     */
    public final boolean isEmpty() {
        boolean result = (root == -1);

        testInvariants();

        return result;
    }

    /**
     * Removes the given object from the collection. Has no effect if
     * the element does not exist in this collection.
     *
     * @param toRemove element to remove
     */
    public final void remove(Object toRemove) {
        internalRemove(toRemove);

        pack();

        testInvariants();
    }

    /**
     * Internal implementation of remove. Removes the given element but does not
     * pack the container after the removal.
     *
     * @param toRemove element to remove
     */
    private void internalRemove(Object toRemove) {
        int objectIndex = getObjectIndex(toRemove);

        if (objectIndex != -1) {
            int parent = parentTree[objectIndex];
            lazyRemoveNode(objectIndex);
            recomputeAncestorTreeSizes(parent);
        }

    }

    /**
     * Removes all elements in the given array from this collection.
     *
     * @param toRemove elements to remove
     */
    public final void removeAll(Object[] toRemove) {
    	Assert.isNotNull(toRemove);

        for (Object object : toRemove) {
            internalRemove(object);
        }
    	pack();
    }

    /**
     * Retains the n smallest items in the collection, removing the rest. When
     * this method returns, the size of the collection will be n. Note that
     * this is a no-op if n > the current size of the collection.
     *
     * Temporarily package visibility until the implementation of FastProgressReporter
     * is finished.
     *
     * @param n number of items to retain
     * @param mon progress monitor
     * @throws InterruptedException if the progress monitor is cancelled in another thread
     */
    /* package */ final void retainFirst(int n, FastProgressReporter mon) throws InterruptedException {
        int sz = size();

        if (n >= sz) {
            return;
        }

        removeRange(n, sz - n, mon);

        testInvariants();
    }

    /**
     * Retains the n smallest items in the collection, removing the rest. When
     * this method returns, the size of the collection will be n. Note that
     * this is a no-op if n > the current size of the collection.
     *
     * @param n number of items to retain
     */
    public final void retainFirst(int n) {
        try {
            retainFirst(n, new FastProgressReporter());
        } catch (InterruptedException e) {
        }

        testInvariants();
    }

    /**
     * Removes all elements in the given range from this collection.
     * For example, removeRange(10, 3) would remove the 11th through 13th
     * smallest items from the collection.
     *
     * @param first 0-based index of the smallest item to remove
     * @param length number of items to remove
     */
    public final void removeRange(int first, int length) {
        try {
            removeRange(first, length, new FastProgressReporter());
        } catch (InterruptedException e) {
        }

        testInvariants();
    }

    /**
     * Removes all elements in the given range from this collection.
     * For example, removeRange(10, 3) would remove the 11th through 13th
     * smallest items from the collection.
     *
     * Temporarily package visiblity until the implementation of FastProgressReporter is
     * finished.
     *
     * @param first 0-based index of the smallest item to remove
     * @param length number of items to remove
     * @param mon progress monitor
     * @throws InterruptedException if the progress monitor is cancelled in another thread
     */
    /* package */ final void removeRange(int first, int length, FastProgressReporter mon) throws InterruptedException {
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

    /**
     * Prunes the given subtree (and all child nodes, sorted or unsorted).
     *
     * @param subTree
     * @since 3.1
     */
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

    /**
     * Schedules the node for removal. If the node can be removed in constant time,
     * it is removed immediately.
     *
     * @param subTree
     * @return the replacement node
     * @since 3.1
     */
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

    /**
     * Removes the given subtree, replacing it with one of its children.
     * Returns the new root of the subtree
     *
     * @param subTree
     * @return the index of the new root
     * @since 3.1
     */
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

    /**
     * Removes all elements from the collection
     */
    public final void clear() {
        lastNode = 0;
        setArraySize(MIN_CAPACITY);
        root = -1;
        firstUnusedNode = -1;
        objectIndices = null;

        testInvariants();
    }

    /**
     * Returns the comparator that is determining the sort order for this collection
     *
     * @return comparator for this collection
     */
    public Comparator getComparator() {
        return comparator;
    }

    /**
     * Fills in an array of size n with the n smallest elements from the collection.
     * Can compute the result in sorted or unsorted order.
     *
     * Currently package visible until the implementation of FastProgressReporter is finished.
     *
     * @param result array to be filled
     * @param sorted if true, the result array will be sorted. If false, the result array
     * may be unsorted. This does not affect which elements appear in the result, only their
     * order.
     * @param mon monitor used to report progress and check for cancellation
     * @return the number of items inserted into the result array. This will be equal to the minimum
     * of result.length and container.size()
     * @throws InterruptedException if the progress monitor is cancelled
     */
    /* package */ final int getFirst(Object[] result, boolean sorted, FastProgressReporter mon) throws InterruptedException {
        int returnValue = getRange(result, 0, sorted, mon);

        testInvariants();

        return returnValue;
    }

    /**
     * Fills in an array of size n with the n smallest elements from the collection.
     * Can compute the result in sorted or unsorted order.
     *
     * @param result array to be filled
     * @param sorted if true, the result array will be sorted. If false, the result array
     * may be unsorted. This does not affect which elements appear in the result. It only
     * affects their order. Computing an unsorted result is asymptotically faster.
     * @return the number of items inserted into the result array. This will be equal to the minimum
     * of result.length and container.size()
     */
    public final int getFirst(Object[] result, boolean sorted) {
        int returnValue = 0;

        try {
            returnValue = getFirst(result, sorted, new FastProgressReporter());
        } catch (InterruptedException e) {
        }

        testInvariants();

        return returnValue;
    }

    /**
     * Given a position defined by k and an array of size n, this fills in the array with
     * the kth smallest element through to the (k+n)th smallest element. For example,
     * getRange(myArray, 10, false) would fill in myArray starting with the 10th smallest item
     * in the collection. The result can be computed in sorted or unsorted order. Computing the
     * result in unsorted order is more efficient.
     * <p>
     * Temporarily set to package visibility until the implementation of FastProgressReporter
     * is finished.
     * </p>
     *
     * @param result array to be filled in
     * @param rangeStart index of the smallest element to appear in the result
     * @param sorted true iff the result array should be sorted
     * @param mon progress monitor used to cancel the operation
     * @throws InterruptedException if the progress monitor was cancelled in another thread
     */
    /* package */ final int getRange(Object[] result, int rangeStart, boolean sorted, FastProgressReporter mon) throws InterruptedException {
        return getRange(result, 0, rangeStart, root, sorted, mon);
    }

    /**
     * Computes the n through n+k items in this collection.
     * Computing the result in unsorted order is more efficient. Sorting the result will
     * not change which elements actually show up in the result. That is, even if the result is
     * unsorted, it will still contain the same elements as would have been at that range in
     * a fully sorted collection.
     *
     * @param result array containing the result
     * @param rangeStart index of the first element to be inserted into the result array
     * @param sorted true iff the result will be computed in sorted order
     * @return the number of items actually inserted into the result array (will be the minimum
     * of result.length and this.size())
     */
    public final int getRange(Object[] result, int rangeStart, boolean sorted) {
        int returnValue = 0;

        try {
            returnValue = getRange(result, rangeStart, sorted, new FastProgressReporter());
        } catch (InterruptedException e) {
        }

        testInvariants();

        return returnValue;
    }

    /**
     * Returns the item at the given index. Indexes are based on sorted order.
     *
     * @param index index to test
     * @return the item at the given index
     */
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

    /**
     * Returns the contents of this collection as a sorted or unsorted
     * array. Computing an unsorted array is more efficient.
     *
     * @param sorted if true, the result will be in sorted order. If false,
     * the result may be in unsorted order.
     * @return the contents of this collection as an array.
     */
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

    /**
     * Fills in the available space in the given array with all children of the given node.
     *
     * @param result
     * @param resultIdx index in the result array where we will begin filling in children
     * @param node
     * @return the number of children added to the array
     * @since 3.1
     */
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

    /**
     * Returns true iff this collection contains the given item
     *
     * @param item item to test
     * @return true iff this collection contains the given item
     */
    public boolean contains(Object item) {
    	Assert.isNotNull(item);
        boolean returnValue = (getObjectIndex(item) != -1);

        testInvariants();

        return returnValue;
    }
