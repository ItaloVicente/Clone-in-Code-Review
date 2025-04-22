			listener.propertyChanged(descriptor, id);
		}
	}

	private ActionSetRec getRec(IActionSetDescriptor descriptor) {
		ActionSetRec rec = (ActionSetRec) actionSets.get(descriptor);

		if (rec == null) {
			rec = new ActionSetRec();
			actionSets.put(descriptor, rec);
		}

		return rec;
	}

	public void showAction(IActionSetDescriptor descriptor) {
		ActionSetRec rec = getRec(descriptor);

		boolean wasVisible = rec.isVisible();
		rec.showCount++;
		if (!wasVisible && rec.isVisible()) {
			visibleItems.add(descriptor);
			firePropertyChange(descriptor, PROP_VISIBLE);
			if (rec.isEmpty()) {
				actionSets.remove(descriptor);
			}
		}
	}

	public void hideAction(IActionSetDescriptor descriptor) {
		ActionSetRec rec = getRec(descriptor);

		boolean wasVisible = rec.isVisible();
		rec.showCount--;
		if (wasVisible && !rec.isVisible()) {
			visibleItems.remove(descriptor);
			firePropertyChange(descriptor, PROP_HIDDEN);
			if (rec.isEmpty()) {
				actionSets.remove(descriptor);
			}
		}
	}

	public void maskAction(IActionSetDescriptor descriptor) {
		ActionSetRec rec = getRec(descriptor);

		boolean wasVisible = rec.isVisible();
		rec.maskCount++;
		if (wasVisible && !rec.isVisible()) {
			visibleItems.remove(descriptor);
			firePropertyChange(descriptor, PROP_HIDDEN);
			if (rec.isEmpty()) {
				actionSets.remove(descriptor);
			}
		}
	}

	public void unmaskAction(IActionSetDescriptor descriptor) {
		ActionSetRec rec = getRec(descriptor);

		boolean wasVisible = rec.isVisible();
		rec.maskCount--;
		if (!wasVisible && rec.isVisible()) {
			visibleItems.add(descriptor);
			firePropertyChange(descriptor, PROP_VISIBLE);
			if (rec.isEmpty()) {
				actionSets.remove(descriptor);
			}
		}
	}

	public Collection getVisibleItems() {
		return visibleItems;
	}

	public void change(IActionSetDescriptor descriptor, int changeType) {
		switch (changeType) {
		case CHANGE_SHOW:
			showAction(descriptor);
			break;
		case CHANGE_HIDE:
			hideAction(descriptor);
			break;
		case CHANGE_MASK:
			maskAction(descriptor);
			break;
		case CHANGE_UNMASK:
			unmaskAction(descriptor);
			break;
		}
	}
