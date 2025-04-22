		}
		return modifyListener;
	}

	protected void handleDefaultSelection(SelectionEvent event) {
		fireApplyEditorValue();
		deactivate();
	}

	@Override
