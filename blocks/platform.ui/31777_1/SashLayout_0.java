
			boolean leftFixed = sr.left.getContainerData().endsWith("px"); //$NON-NLS-1$
			boolean rightFixed = sr.right.getContainerData().endsWith("px"); //$NON-NLS-1$
			int leftWeight;
			int rightWeight;
			if (!leftFixed && !rightFixed) {
				leftWeight = getWeight(sr.left);
				rightWeight = getWeight(sr.right);
			} else if (leftFixed && rightFixed) {
				int leftSize = getFixed(sr.left);
				int rightSize = getFixed(sr.right);
				int total = leftSize + rightSize;
				if (total != 0) {
					leftWeight = leftSize * 100 / total;
				} else {
					leftWeight = 50;
				}
				rightWeight = 100 - leftWeight;
			} else if (leftFixed) {
				rightWeight = getWeight(sr.right);
				leftWeight = 100 - rightWeight;
			} else {
				leftWeight = getWeight(sr.left);
				rightWeight = 100 - leftWeight;

			}
			int totalWeight = leftWeight + rightWeight;
