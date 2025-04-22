	public void setPreselect(ObjectId commitId) {
		preselect = commitId;
	}

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
	public void addListener(int evtType, Listener mouseListener) {
		patternField.addListener(evtType, mouseListener);
	}

	@Override
	public void removeListener(int evtType, Listener mouseListener) {
		patternField.removeListener(evtType, mouseListener);
	}

	@Override
	public void addKeyListener(KeyListener keyListener) {
		patternField.addKeyListener(keyListener);
	}

	@Override
	public void removeKeyListener(KeyListener keyListener) {
		patternField.removeKeyListener(keyListener);
	}

	public void addStatusListener(StatusListener layoutListener) {
		layoutListeners.addIfAbsent(layoutListener);
	}

	public void removeStatusListener(StatusListener layoutListener) {
		layoutListeners.remove(layoutListener);
	}

	private void notifyStatus(String text) {
		for (StatusListener l : layoutListeners) {
			l.setMessage(this, text);
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
			finder.setUser(false);
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
			notifyStatus(current + '/' + findResults.size());
		}
	}

