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
			return ImageDescriptor.createFromImageDataProvider(zoom -> fLabelProvider.getImage(fFile).getImageData());
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
