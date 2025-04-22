				final FetchResult result = (FetchResult) inputElement;
				TrackingRefUpdate[] updates = result.getTrackingRefUpdates()
						.toArray(new TrackingRefUpdate[0]);
				FetchResultAdapter[] elements = new FetchResultAdapter[updates.length];
				for (int i = 0; i < elements.length; i++)
					elements[i] = new FetchResultAdapter(updates[i]);
				return elements;
