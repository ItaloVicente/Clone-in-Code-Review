				double pct = (curY - top) / (bottom - top);
				leftWeight = (int) ((totalWeight * pct) + 0.5);
				if (leftWeight < minSashValue)
					leftWeight = minSashValue;
				if (leftWeight > (totalWeight - minSashValue))
					leftWeight = totalWeight - minSashValue;
				rightWeight = totalWeight - leftWeight;
