				if (part instanceof IEditorPart) {
					IEditorInput input = ((IEditorPart) part).getEditorInput();
					Repository repository = AdapterUtils.adapt(input,
							Repository.class);
					if (repository != null) {
						reactOnSelection(new StructuredSelection(repository));
					}
				} else {
					reactOnSelection(selection);
