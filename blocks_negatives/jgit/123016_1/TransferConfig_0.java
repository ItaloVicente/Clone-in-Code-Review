					boolean add = true;
					for (String hide : hideRefs) {
						if (e.getKey().equals(hide) || prefixMatch(hide, e.getKey())) {
							add = false;
							break;
						}
					}
					if (add)
