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
