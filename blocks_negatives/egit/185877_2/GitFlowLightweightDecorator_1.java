			@Override
			public ImageData getImageData() {
				if (data == null) {
					data = descriptor.getImageData();
				}
				return data;
			}
