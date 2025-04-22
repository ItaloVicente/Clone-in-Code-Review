	/**
	 * Used to render the right tree in case we have no workspace
	 */
	private final class RightTreeContentProvider extends
			AbstractPathNodeContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			if (rightPathsWithChildren.isEmpty() && rightOnly.isEmpty())
				return new String[] { UIText.CompareTreeView_NoDifferencesFoundMessage };
			return super.getElements(inputElement);
		}

		public Object[] getChildren(Object parentElement) {
			IPath parent = ((PathNode) parentElement).path;
			List<PathNode> children = new ArrayList<PathNode>();
			for (IPath childPath : rightPathsWithChildren) {
				if (childPath.segmentCount() > 0
						&& childPath.removeLastSegments(1).equals(parent)) {
					children.add(new PathNode(childPath, Type.FOLDER));
				}
			}
			for (IPath mapPath : rightVersionMap.keySet()) {
				if (mapPath.removeLastSegments(1).equals(parent)
						&& (!showDeletedOnly || rightOnly.contains(mapPath))
						&& (showEquals || !equalIds.contains(mapPath))) {
					if (rightOnly.contains(mapPath))
						children.add(new PathNode(mapPath, Type.FILE_DELETED));
					else if (equalIds.contains(mapPath))
						children.add(new PathNode(mapPath,
								Type.FILE_BOTH_SIDES_SAME));
					else
						children.add(new PathNode(mapPath,
								Type.FILE_BOTH_SIDES_DIFFER));
				}
			}
			Collections.sort(children, new Comparator<PathNode>() {
				public int compare(PathNode o1, PathNode o2) {
					int diff = o1.type.ordinal() - o2.type.ordinal();
					if (diff != 0)
						return diff;
					return o1.path.toString().compareTo(o2.path.toString());
				}
			});
			return children.toArray();
		}

		public boolean hasChildren(Object element) {
