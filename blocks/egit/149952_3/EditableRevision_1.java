		@Override
		public void connect(IDocumentProvider provider,
				IEditorInput documentKey) throws CoreException {
			if (documentKey instanceof FakeResourceStorageEditorInput) {
				FakeResourceStorageEditorInput input = (FakeResourceStorageEditorInput) documentKey;
				try {
					input.setAdapt(false);
					super.connect(provider, input);
				} finally {
					input.setAdapt(true);
				}
			} else {
				super.connect(provider, documentKey);
			}
		}

