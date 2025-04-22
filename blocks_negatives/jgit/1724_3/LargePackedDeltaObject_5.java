			@Override
			protected InputStream openBase() throws IOException {
				InputStream in;
				if (base instanceof LargePackedDeltaObject)
					in = ((LargePackedDeltaObject) base).open(wc);
				else
					in = base.openStream();
				if (baseSize == SIZE_UNKNOWN) {
					if (in instanceof DeltaStream)
						baseSize = ((DeltaStream) in).getSize();
					else if (in instanceof ObjectStream)
						baseSize = ((ObjectStream) in).getSize();
				}
				return in;
			}
