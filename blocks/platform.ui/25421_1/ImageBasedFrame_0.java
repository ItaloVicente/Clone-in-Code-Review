	
	private void sendImageDisposedEvent(PaintEvent event) {
		Event dndHandleDisposedEvent = new Event();
		dndHandleDisposedEvent.display = event.display;
		dndHandleDisposedEvent.widget = event.widget;
		dndHandleDisposedEvent.gc = event.gc;
		dndHandleDisposedEvent.height = event.height;
		dndHandleDisposedEvent.width = event.width;
		dndHandleDisposedEvent.x = event.x;
		dndHandleDisposedEvent.y = event.y;
		dndHandleDisposedEvent.data = event.data;
		dndHandleDisposedEvent.count = event.count;
		dndHandleDisposedEvent.time = event.time;
		notifyListeners(ImageDisposed, dndHandleDisposedEvent);		
	}
