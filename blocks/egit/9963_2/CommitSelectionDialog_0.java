				if (path != null && !"".equals(path)) { //$NON-NLS-1$
					if (resource.getType() == IResource.FILE)
						filters.add(FollowFilter.create(path));
					else
						filters.add(AndTreeFilter.create(
								PathFilter.create(path), TreeFilter.ANY_DIFF));
				}
