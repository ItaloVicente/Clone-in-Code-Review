	@Override
	public boolean setFocus() {
		return patternField.setFocus();
	}

	public void setText(String text, boolean search) {
		if (!search) {
			patternField.removeModifyListener(patternModifyListener);
		}
		patternField.setText(text);
		if (!search) {
			patternField.addModifyListener(patternModifyListener);
		}
	}

	public void setText(String text) {
		setText(text, false);
	}

	public String getText() {
		return patternField.getText();
	}

	@Override
	public void addKeyListener(KeyListener keyListener) {
		patternField.addKeyListener(keyListener);
	}

	@Override
	public void removeKeyListener(KeyListener keyListener) {
		patternField.removeKeyListener(keyListener);
	}

	public void addLayoutListener(LayoutListener layoutListener) {
		layoutListeners.addIfAbsent(layoutListener);
	}

	public void removeLayoutListener(LayoutListener layoutListener) {
		layoutListeners.remove(layoutListener);
	}

	private void notifyLayout() {
		for (LayoutListener l : layoutListeners) {
			l.layoutChange(this);
		}
	}

	private void findNext() {
		find(true);
	}

	private void findPrevious() {
		find(false);
	}

	private void find(boolean next) {
		if (patternField.getText().length() > 0 && findResults.size() == 0) {
			final FindToolbarJob finder = createFinder();
			finder.setUser(true);
			finder.schedule();
			patternField.setSelection(0, 0);
		} else {
			int currentIx = historyTable.getSelectionIndex();
			int newIx = -1;
			if (next) {
				newIx = findResults.getIndexAfter(currentIx);
				if (newIx == -1) {
					newIx = findResults.getFirstIndex();
				}
			} else {
				newIx = findResults.getIndexBefore(currentIx);
				if (newIx == -1) {
					newIx = findResults.getLastIndex();
				}
			}
			notifyListeners(newIx);

			String current = null;
			currentPosition = findResults.getMatchNumberFor(newIx);
			if (currentPosition == -1) {
				current = "-"; //$NON-NLS-1$
			} else {
				current = String.valueOf(currentPosition);
			}
			currentPositionLabel.setText(current + '/' + findResults.size());
			currentPositionLabel.requestLayout();
			notifyLayout();
		}
	}

