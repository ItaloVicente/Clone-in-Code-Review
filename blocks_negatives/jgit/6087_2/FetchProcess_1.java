			try {
				final TrackingRefUpdate tru = createUpdate(spec, newId);
				if (newId.equals(tru.getOldObjectId()))
					return;
				localUpdates.add(tru);
			} catch (IOException err) {
				throw new TransportException( MessageFormat.format(
						JGitText.get().cannotResolveLocalTrackingRefForUpdating, spec.getDestination()), err);
			}
