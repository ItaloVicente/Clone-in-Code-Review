			field.addBidiSegmentListener(new BidiSegmentListener() {
				@Override
				public void lineGetSegments(BidiSegmentEvent event) {
					listener.getSegments(event);
				}
			});
