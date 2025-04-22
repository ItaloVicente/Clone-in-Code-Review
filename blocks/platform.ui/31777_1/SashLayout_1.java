			if (leftFixed) {
				setWeight(sr.left,
						((int) (leftWeight * totalSize / totalWeight + 0.5))
								+ "px"); //$NON-NLS-1$
			} else {
				setWeight(sr.left, leftWeight + "%"); //$NON-NLS-1$
			}
			if (rightFixed) {
				setWeight(sr.right, ((int) (rightWeight * totalSize
						/ totalWeight + 0.5))
						+ "px"); //$NON-NLS-1$
			} else
				setWeight(sr.right, rightWeight + "%"); //$NON-NLS-1$
