	private static final class AdaptedValueProperty<S, T> extends SimpleValueProperty<S, T> {
		private final Class<T> adapter;
		private final IAdapterManager adapterManager;

		private AdaptedValueProperty(Class<T> adapter, IAdapterManager adapterManager) {
			this.adapter = adapter;
			this.adapterManager = adapterManager;
		}

		@Override
		public Object getValueType() {
			return adapter;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected T doGetValue(S source) {
			if (adapter.isInstance(source))
				return (T) source;
			return adapterManager.getAdapter(source, adapter);
		}

		@Override
		protected void doSetValue(S source, T value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ValueDiff<? extends T>> listener) {
			return null;
		}
	}

	private static class SingleSelectionProperty<S extends ISelectionService, T> extends SimpleValueProperty<S, T> {
		private final String partId;
		private final boolean post;
		private final Object elementType;

		private SingleSelectionProperty(String partId, boolean post, Object elementType) {
			this.partId = partId;
			this.post = post;
			this.elementType = elementType;
		}

		@Override
		public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ValueDiff<? extends T>> listener) {
			return new SelectionServiceListener<>(this, listener, partId, post);
		}

		@Override
		protected T doGetValue(S source) {
			ISelection selection;
			if (partId != null) {
				selection = ((ISelectionService) source).getSelection(partId);
			} else {
				selection = ((ISelectionService) source).getSelection();
			}
			if (selection instanceof IStructuredSelection) {
				@SuppressWarnings("unchecked")
				T elem = (T) ((IStructuredSelection) selection).getFirstElement();
				return elem;
			}
			return null;
		}

		@Override
		protected void doSetValue(S source, T value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object getValueType() {
			return elementType;
		}
	}

	private static class MultiSelectionProperty<S extends ISelectionService, E> extends SimpleListProperty<S, E> {
		private final String partId;
		private final boolean post;
		private final Object elementType;

		MultiSelectionProperty(String partId, boolean post, Object elementType) {
			this.partId = partId;
			this.post = post;
			this.elementType = elementType;
		}

		@Override
		public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ListDiff<E>> listener) {
			return new SelectionServiceListener<>(this, listener, partId, post);
		}

		@Override
		public Object getElementType() {
			return elementType;
		}

		@Override
		protected List<E> doGetList(S source) {
			ISelection selection;
			if (partId != null) {
				selection = ((ISelectionService) source).getSelection(partId);
			} else {
				selection = ((ISelectionService) source).getSelection();
			}
			if (selection instanceof IStructuredSelection) {
				List<E> list = ((IStructuredSelection) selection).toList();
				return new ArrayList<>(list);
			}
			return Collections.emptyList();
		}

		@Override
		protected void doSetList(S source, List<E> list, ListDiff<E> diff) {
			throw new UnsupportedOperationException();
		}
	}

	private static class SelectionServiceListener<S extends ISelectionService, D extends IDiff>
			extends NativePropertyListener<S, D>
			implements ISelectionListener {
		private final String partId;
		private final boolean post;

		private SelectionServiceListener(IProperty property, ISimplePropertyListener<S, D> wrapped, String partID,
				boolean post) {
			super(property, wrapped);
			this.partId = partID;
			this.post = post;
		}

		@Override
		protected void doAddTo(S source) {
			ISelectionService selectionService = source;
			if (post) {
				if (partId != null) {
					selectionService.addPostSelectionListener(partId, this);
				} else {
					selectionService.addPostSelectionListener(this);
				}
			} else {
				if (partId != null) {
					selectionService.addSelectionListener(partId, this);
				} else {
					selectionService.addSelectionListener(this);
				}
			}
		}

		@Override
		protected void doRemoveFrom(S source) {
			ISelectionService selectionService = source;
			if (post) {
				if (partId != null) {
					selectionService.removePostSelectionListener(partId, this);
				} else {
					selectionService.removePostSelectionListener(this);
				}
			} else {
				if (partId != null) {
					selectionService.removeSelectionListener(partId, this);
				} else {
					selectionService.removeSelectionListener(this);
				}
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			fireChange((S) part, null);
		}
	}
