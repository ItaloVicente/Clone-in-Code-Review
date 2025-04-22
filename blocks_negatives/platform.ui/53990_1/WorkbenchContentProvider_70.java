		return new Runnable(){
			@Override
			public void run() {
				((StructuredViewer) viewer).refresh(resource);
			}
		};
