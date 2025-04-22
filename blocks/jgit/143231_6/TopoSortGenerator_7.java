			}
			for (int i = 0; i < c.parents.length; i++) {
				if (firstParent && i > 0) {
					break;
				}
				c.parents[i].inDegree++;
			}
