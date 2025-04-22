			}
		}
		return elements;
	}

	public void restoreState(IMemento memento) {
		IMemento childMem = memento.getChild(TAG_FRAME_INPUT);

		if (childMem == null) {
