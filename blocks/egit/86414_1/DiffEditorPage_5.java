	private static class DiffDocumentProvider extends AbstractDocumentProvider {

		@Override
		protected IDocument createDocument(Object element)
				throws CoreException {
			if (element instanceof DiffEditorInput) {
				return ((DiffEditorInput) element).getDocument();
			}
			return new Document();
		}

		@Override
		protected IAnnotationModel createAnnotationModel(Object element)
				throws CoreException {
			return new AnnotationModel();
		}

		@Override
		protected void doSaveDocument(IProgressMonitor monitor, Object element,
				IDocument document, boolean overwrite) throws CoreException {
		}

		@Override
		protected IRunnableContext getOperationRunner(
				IProgressMonitor monitor) {
			return null;
		}

