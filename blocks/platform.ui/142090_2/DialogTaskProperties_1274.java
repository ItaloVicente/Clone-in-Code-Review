		attrs.put(IMarker.PRIORITY, getPriorityFromDialog());
		attrs.put(IMarker.DONE, completedCheckbox.getSelection() ? Boolean.TRUE : Boolean.FALSE);
		Object userEditable = attrs.get(IMarker.USER_EDITABLE);
		if (userEditable == null || !(userEditable instanceof Boolean)) {
			attrs.put(IMarker.USER_EDITABLE, Boolean.TRUE);
		}
		return attrs;
	}
