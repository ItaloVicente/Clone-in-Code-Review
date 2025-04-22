		} catch (IOException e) {
			conflictListFailure = true;
		} finally {
			if (rw != null)
				rw.release();
			if (dc != null)
				dc.unlock();
		}

		if (conflictListFailure) {
			Label failureLabel = new Label(main, SWT.NONE);
			failureLabel
					.setText(UIText.RebaseResultDialog_ConflictListFailureMessage);
		} else {
			Label conflictListLabel = new Label(main, SWT.NONE);
			conflictListLabel
					.setText(UIText.RebaseResultDialog_DiffDetailsLabel);
			TableViewer conflictList = new TableViewer(main, SWT.BORDER);
			GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(
					conflictList.getTable());
			conflictList.setContentProvider(ArrayContentProvider.getInstance());
			conflictList.setInput(conflictPaths);
