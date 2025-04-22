	static class StagingDragListener extends DragSourceAdapter {

		private ISelectionProvider provider;

		public StagingDragListener(ISelectionProvider provider) {
			this.provider = provider;
		}

		public void dragStart(DragSourceEvent event) {
			event.doit = !provider.getSelection().isEmpty();
		}

		public void dragFinished(DragSourceEvent event) {
			if (LocalSelectionTransfer.getTransfer().isSupportedType(
					event.dataType))
				LocalSelectionTransfer.getTransfer().setSelection(null);
		}

		public void dragSetData(DragSourceEvent event) {
			IStructuredSelection selection = (IStructuredSelection) provider
					.getSelection();
			if (selection.isEmpty())
				return;

			if (LocalSelectionTransfer.getTransfer().isSupportedType(
					event.dataType)) {
				LocalSelectionTransfer.getTransfer().setSelection(selection);
				return;
			}

			if (FileTransfer.getInstance().isSupportedType(event.dataType)) {
				List<String> files = new ArrayList<String>();
				for (Object selected : selection.toList())
					if (selected instanceof StagingEntry) {
						StagingEntry entry = (StagingEntry) selected;
						File file = new File(
								entry.getRepository().getWorkTree(),
								entry.getPath());
						if (file.exists())
							files.add(file.getAbsolutePath());
					}
				if (!files.isEmpty()) {
					event.data = files.toArray(new String[files.size()]);
					return;
				}
			}
		}
	}

