			public String getToolTipText(final Object element) {
				if (element instanceof FetchResultAdapter) {
					switch (((FetchResultAdapter) element).update.getResult()) {
					case FAST_FORWARD:
						return UIText.FetchResultTable_statusDetailFastForward;
					case FORCED:
					case REJECTED:
						return UIText.FetchResultTable_statusDetailNonFastForward;
					case IO_FAILURE:
						return UIText.FetchResultTable_statusDetailIOError;
					case LOCK_FAILURE:
						return UIText.FetchResultTable_statusDetailCouldntLock;
					default:
						return super.getToolTipText(element);
					}
				}
				return super.getToolTipText(element);
