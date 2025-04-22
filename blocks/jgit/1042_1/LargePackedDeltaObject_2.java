					in = ((LargePackedDeltaObject) base).open(wc);
				else
					in = base.openStream();
				if (baseSize < 0) {
					if (in instanceof DeltaStream)
						baseSize = ((DeltaStream) in).getSize();
					else if (in instanceof ObjectStream)
						baseSize = ((ObjectStream) in).getSize();
				}
				return in;
