				}
			}
			gatherState(checked, grayed, item);
		}
	}

	@Override
	public boolean getChecked(E element) {
		Widget widget = findItem(element);
		if (widget instanceof TreeItem) {
