	private void withSelection(Consumer<FileDiff> consumer) {
		IStructuredSelection selection = getStructuredSelection();
		if (selection == null || selection.isEmpty()) {
			return;
		}
		Iterator<?> items = selection.iterator();
		items.forEachRemaining(o -> {
			if (o instanceof FileDiff) {
				consumer.accept((FileDiff) o);
			}
		});
	}

	private void withFirstSelected(Consumer<FileDiff> consumer) {
		IStructuredSelection selection = getStructuredSelection();
		if (selection == null || selection.isEmpty()) {
			return;
		}
		Object o = selection.getFirstElement();
		if (o instanceof FileDiff) {
			consumer.accept((FileDiff) o);
		}
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		copyAll.setEnabled(
				getContentProvider() instanceof IStructuredContentProvider
						&& doGetItemCount() > 0);
	}

