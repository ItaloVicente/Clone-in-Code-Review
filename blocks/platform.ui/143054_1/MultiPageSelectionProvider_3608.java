		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
