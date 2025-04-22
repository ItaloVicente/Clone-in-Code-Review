			getControl().addListener(SWT.SetData, new Listener() {

				@Override
				public void handleEvent(Event event) {
					Item item = (Item) event.item;
					final int index = doIndexOf(item);
