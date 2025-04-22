			final ObjectReader dr = delegate().newReader();
			return new ObjectReader.Filter() {
				@Override
				protected ObjectReader delegate() {
					return dr;
				}

				@Override
				public ObjectInserter getCreatedFromInserter() {
					return ObjectInserter.Filter.this;
				}
			};
