			return new Runnable(){
				@Override
				public void run() {
					((StructuredViewer) viewer).update(resource, null);
				}
			};
