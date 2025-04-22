	private abstract class Preview
			implements Observer {

		protected PreferenceStore store = new PreferenceStore();
		protected final TreeViewer fViewer;
		private Composite composite;
		private Composite parent;

		protected final ResourceManager fImageCache = new LocalResourceManager(
				JFaceResources.getResources());

		public Preview(Composite parent) {
			this.parent = parent;
			composite = SWTUtils.createHVFillComposite(parent, SWTUtils.MARGINS_NONE);

			SWTUtils.createLabel(composite, UIText.DecoratorPreferencesPage_preview);

			fViewer = new TreeViewer(composite);
			fViewer.getControl().setLayoutData(SWTUtils.createHVFillGridData());
		}

		public void update(Observable o, Object arg) {
			refresh();
		}

		public abstract void refresh();

		public void dispose() {
			fImageCache.dispose();
		}

		public void hide() {
			((GridData)composite.getLayoutData()).exclude = true;	// ignore by layout
			composite.setVisible(false);
			composite.layout();
			parent.layout();
		}

		public void show() {
			((GridData)composite.getLayoutData()).exclude = false;	// ignore by layout
			composite.setVisible(true);
			composite.layout();
			parent.layout();
		}
	}

	private class ChangeSetPreview extends Preview
			implements Observer, ITreeContentProvider {

		public ChangeSetPreview(Composite composite) {
			super(composite);
			fViewer.setContentProvider(this);
			fViewer.setLabelProvider(new LabelProvider() {

				@Override
				public Image getImage(Object element) {
					if (element instanceof GitModelCommitMokeup) {
						return fImageCache.createImage(UIIcons.CHANGESET);
					}

					return super.getImage(element);
				}

				public String getText(Object element) {
					if (element instanceof GitModelCommitMokeup) {
						String format = store.getString(UIPreferences.SYNC_VIEW_CHANGESET_LABEL_FORMAT);
						String dateFormat = store.getString(UIPreferences.DATE_FORMAT);
						return ((GitModelCommitMokeup)element).getMokeupText(format, dateFormat);
					}
					return super.getText(element);
				}
			});
			fViewer.setContentProvider(this);
			fViewer.setInput(new GitModelCommitMokeup());
		}

		public Object[] getChildren(Object parentElement) {
			return new Object[0];
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return false;
		}

		public Object[] getElements(Object inputElement) {
			return new Object[] { inputElement };
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void refresh() {
			store = new PreferenceStore();
			performOk(store);
			fViewer.refresh(true);
		}
	}


