					l.checkStateChanged(event);
				}
			});
		}
	}

	@Override
	public boolean getChecked(E element) {
		Widget widget = findItem(element);
		if (widget instanceof TableItem) {
			return ((TableItem) widget).getChecked();
		}
		return false;
	}

