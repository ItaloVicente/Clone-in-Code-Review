				if (!(inputElement instanceof FetchResult)) {
					return new Object[0];
				}
				FetchResult result = (FetchResult) inputElement;
				return result.getTrackingRefUpdates().stream()
						.map(FetchResultAdapter::new).toArray();
