				if (lastSegment != null) {
					Object mapped = element2TreePaths.get(lastSegment);
					if (mapped == null) {
						selection.add(lastSegment);
						element2TreePaths.put(lastSegment, paths[i]);
					} else if (mapped instanceof List) {
						((List) mapped).add(paths[i]);
					} else {
						List newMapped = new ArrayList(2);
						newMapped.add(mapped);
						newMapped.add(paths[i]);
						element2TreePaths.put(lastSegment, newMapped);
					}
