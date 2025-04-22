			listChangeListener = new IListChangeListener<E>() {
				@Override
				public void handleListChange(ListChangeEvent<? extends E> event) {
					DecoratingObservableList.this.handleListChange(event);
				}
			};
