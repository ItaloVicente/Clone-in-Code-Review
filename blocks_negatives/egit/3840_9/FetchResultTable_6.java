			@Override
			public String getToolTipText(final Object element) {
				final Result result = ((TrackingRefUpdate) element).getResult();
				switch (result) {
				case FAST_FORWARD:
					return UIText.FetchResultTable_statusDetailFastForward;
				case FORCED:
				case REJECTED:
					return UIText.FetchResultTable_statusDetailNonFastForward;
				case NEW:
				case NO_CHANGE:
					return null;
				case IO_FAILURE:
					return UIText.FetchResultTable_statusDetailIOError;
				case LOCK_FAILURE:
					return UIText.FetchResultTable_statusDetailCouldntLock;
				default:
					throw new IllegalArgumentException(NLS.bind(
							UIText.FetchResultTable_statusUnexpected, result));
				}
