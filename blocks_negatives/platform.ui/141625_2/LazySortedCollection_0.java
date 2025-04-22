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
