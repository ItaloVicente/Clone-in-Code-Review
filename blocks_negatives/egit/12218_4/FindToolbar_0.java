					switch (store.getInt(UIPreferences.FINDTOOLBAR_FIND_IN)) {
					case PREFS_FINDIN_COMMENTS:
						commentsItem.notifyListeners(SWT.Selection, null);
						break;
					case PREFS_FINDIN_AUTHOR:
						authorItem.notifyListeners(SWT.Selection, null);
						break;
					case PREFS_FINDIN_COMMITID:
						commitIdItem.notifyListeners(SWT.Selection, null);
						break;
					case PREFS_FINDIN_COMMITTER:
						committerItem.notifyListeners(SWT.Selection, null);
						break;
					}
