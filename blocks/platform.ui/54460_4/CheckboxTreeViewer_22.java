		return false;
	}

	public Object[] getCheckedElements() {
		List<Object> v = new ArrayList<>();
		Control tree = getControl();
		internalCollectChecked(v, tree);
		return v.toArray();
	}

	public boolean getGrayed(E element) {
		Widget widget = findItem(element);
		if (widget instanceof TreeItem) {
			return ((TreeItem) widget).getGrayed();
		}
		return false;
	}

	public Object[] getGrayedElements() {
		List<Object> result = new ArrayList<>();
		internalCollectGrayed(result, getControl());
		return result.toArray();
	}

	@Override
