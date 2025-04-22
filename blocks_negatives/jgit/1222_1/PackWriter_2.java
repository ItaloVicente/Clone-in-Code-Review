				int cmp = a.getType() - b.getType();
				if (cmp == 0)
					cmp = (a.getPathHash() >>> 1) - (b.getPathHash() >>> 1);
				if (cmp == 0)
					cmp = (a.getPathHash() & 1) - (b.getPathHash() & 1);
				if (cmp == 0)
					cmp = b.getWeight() - a.getWeight();
				return cmp;
