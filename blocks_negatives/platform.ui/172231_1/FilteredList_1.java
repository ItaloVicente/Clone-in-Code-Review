			if (fUpdateJob == null) {
				fList.setSelection(selection);
				fList.notifyListeners(SWT.Selection, new Event());
			} else {
				fUpdateJob.updateSelection(selection);
			}
