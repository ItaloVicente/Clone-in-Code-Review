				if (r == RefUpdate.Result.REJECTED)
					return UIText.FetchResultTable_statusRejected;
				if (r == RefUpdate.Result.NO_CHANGE)
					return UIText.FetchResultTable_statusUpToDate;
				throw new IllegalArgumentException(NLS.bind(
						UIText.FetchResultTable_statusUnexpected, r));
			}
