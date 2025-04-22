	private void updateFileSectionText() {
		filesSection.setText(MessageFormat.format(UIText.CommitDialog_Files,
				Integer.valueOf(filesViewer.getCheckedElements().length),
				Integer.valueOf(filesViewer.getTable().getItemCount())));
	}

