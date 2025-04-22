	private static class ResourceElement extends QuickAccessElement {
		private final WorkbenchLabelProvider fLabelProvider;
		private final IResource fResource;

		private ResourceElement(WorkbenchLabelProvider labelProvider, IResource resource) {
			fLabelProvider = labelProvider;
			fResource = resource;
		}

		@Override
		public String getLabel() {
			return fLabelProvider.getText(fResource);
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			ImageData imageData = fLabelProvider.getImage(fResource).getImageData();
			return ImageDescriptor.createFromImageDataProvider(zoom -> imageData);
		}

		@Override
		public String getId() {
			return fResource.getFullPath().toString();
		}

		@Override
		public void execute() {
			try {
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), (IFile) fResource);
			} catch (PartInitException e) {
				IDEWorkbenchPlugin.log(e.getMessage(), e);
			}
		}
	}
