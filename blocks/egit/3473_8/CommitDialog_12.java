	private void updateFileSectionText() {
		filesSection.setText(MessageFormat.format(
				"Files ({0}/{1})", //$NON-NLS-1$
				Integer.valueOf(filesViewer.getCheckedElements().length),
				Integer.valueOf(filesViewer.getTable().getItemCount())));
	}

