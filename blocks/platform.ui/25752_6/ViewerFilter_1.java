		}
		return out.toArray();
	}

	public Object[] filter(Viewer viewer, TreePath parentPath, Object[] elements) {
		return filter(viewer, parentPath.getLastSegment(), elements);
	}
