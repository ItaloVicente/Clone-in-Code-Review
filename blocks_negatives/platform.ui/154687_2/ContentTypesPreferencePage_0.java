			@Override
			public Image getImage(Object element) {
				Image res = ((IEditorDescriptor) element).getImageDescriptor().createImage();
				if (res != null) {
					disposableEditorIcons.add(res);
				}
				return res;
			}
		});
