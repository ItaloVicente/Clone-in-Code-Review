					if (!useIndex) {
						if (metadata == null) {
							metadata = new CheckoutMetadata(
									tw.getEolStreamType(), tw.getFilterCommand(
											Constants.ATTR_FILTER_TYPE_SMUDGE));
						}
