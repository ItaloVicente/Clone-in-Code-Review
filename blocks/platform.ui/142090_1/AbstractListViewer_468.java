			}
		} else {
			doUpdateItem(list, element, true);
		}
	}

	protected int listGetTopIndex(){
		return -1;
	}

	protected void listSetTopIndex(int index) {
	}

	private void internalRemove(final Object[] elements) {
		Object input = getInput();
		for (Object element : elements) {
			if (equals(element, input)) {
				setInput(null);
				return;
			}
			int ix = getElementIndex(element);
			if (ix >= 0) {
				listRemove(ix);
				listMap.remove(ix);
				unmapElement(element, getControl());
			}
		}
	}

	public void remove(final Object[] elements) {
		assertElementsNotNull(elements);
		if (elements.length == 0) {
			return;
		}
		preservingSelection(() -> internalRemove(elements));
	}

	public void remove(Object element) {
		remove(new Object[] { element });
	}

