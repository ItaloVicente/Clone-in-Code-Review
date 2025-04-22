					if (filters != null)
						for (IResourceFilterDescription filter : filters) {
							FilterCopy child = new FilterCopy(UIResourceFilterDescription.wrap(filter));
							child.parent = this;
							children.add(child);
						}
