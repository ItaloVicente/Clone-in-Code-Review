		return false;
	}

	public Object[] getCheckedElements() {
		ArrayList v = new ArrayList();
		Control tree = getControl();
		internalCollectChecked(v, tree);
		return v.toArray();
	}

	public boolean getGrayed(Object element) {
		Widget widget = findItem(element);
		if (widget instanceof TreeItem) {
			return ((TreeItem) widget).getGrayed();
		}
		return false;
	}

	public Object[] getGrayedElements() {
		List result = new ArrayList();
		internalCollectGrayed(result, getControl());
		return result.toArray();
	}

	@Override
