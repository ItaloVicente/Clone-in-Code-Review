	private static class ResourceElement extends QuickAccessElement {
		private final WorkbenchLabelProvider fLabelProvider;
		private final IFile fFile;

		private ResourceElement(WorkbenchLabelProvider labelProvider, IFile resource) {
			fLabelProvider = labelProvider;
			fFile = resource;
		}

		@Override
		public String getLabel() {
			return fLabelProvider.getText(fFile);
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			ImageData imageData = fLabelProvider.getImage(fFile).getImageData();
			return ImageDescriptor.createFromImageDataProvider(zoom -> imageData);
		}

		@Override
		public String getId() {
			return fFile.getFullPath().toString();
		}

		@Override
		public void execute() {
			try {
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), fFile);
			} catch (PartInitException e) {
				IDEWorkbenchPlugin.log(e.getMessage(), e);
			}
		}
	}
