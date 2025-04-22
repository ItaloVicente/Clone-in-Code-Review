                if (part instanceof IEditorPart) {
                    final IEditorPart editorPart = (IEditorPart) part;
                    return new StructuredSelection(new Object[] { editorPart
                            .getEditorInput() });
                }

                return new StructuredSelection(new Object[0]);
            }

            @Override
			public void removeSelectionChangedListener(
                    ISelectionChangedListener listener) {
                throw new UnsupportedOperationException(
                "This ISelectionProvider is static, and cannot be modified."); //$NON-NLS-1$
            }

            @Override
