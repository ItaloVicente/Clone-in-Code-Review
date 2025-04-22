                }
            }
            gatherState(checked, grayed, item);
        }
    }

    @Override
	public boolean getChecked(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TreeItem) {
