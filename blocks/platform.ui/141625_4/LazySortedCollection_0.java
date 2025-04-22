        	switch (direction) {
        	case DIR_LEFT:
        		leftSubTree[startNode] = newNode;
        		break;
        	case DIR_RIGHT:
        		rightSubTree[startNode] = newNode;
        		break;
        	case DIR_UNSORTED:
        		nextUnsorted[startNode] = newNode;
        		break;
        	case DIR_ROOT:
        		root = newNode;
        		break;
        	case DIR_UNUSED:
        		firstUnusedNode = newNode;
        		break;
        	default:
        		break;
        	}

        	if (newNode != -1) {
        		parentTree[newNode] = startNode;
        	}
