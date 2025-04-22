			@Override
			public Color getBackground(final Object element) {
				final Result result = ((TrackingRefUpdate) element).getResult();
				switch (result) {
				case FAST_FORWARD:
				case FORCED:
				case NEW:
					return updatedColor;
				case NO_CHANGE:
					return upToDateColor;
				case IO_FAILURE:
				case LOCK_FAILURE:
				case REJECTED:
					return rejectedColor;
				default:
					throw new IllegalArgumentException(NLS.bind(
							UIText.FetchResultTable_statusUnexpected, result));
				}
