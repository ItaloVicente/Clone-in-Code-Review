			try {
				ObjectLoader ol = repository.openBlob(id);
				final byte[] bytes = ol.getBytes();
				storage = new IEncodedStorage() {
					public Object getAdapter(Class adapter) {
						return null;
