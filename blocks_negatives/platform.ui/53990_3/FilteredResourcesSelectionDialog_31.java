				if (s1Dot != -1 || s2Dot != -1) {
					comparability = collator.compare(s1, s2);
					if (comparability != 0)
						return comparability;
				}

				if (searchContainer != null) {
					IContainer c1 = resource1.getParent();
					IContainer c2 = resource2.getParent();

					comparability = pathDistance(c1) - pathDistance(c2);
					if (comparability != 0)
						return comparability;
				}
