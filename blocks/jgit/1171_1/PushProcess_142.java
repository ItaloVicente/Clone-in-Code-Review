			monitor.beginTask(PROGRESS_OPENING_CONNECTION
					ProgressMonitor.UNKNOWN);

			final PushResult res = new PushResult();
			connection = transport.openPush();
			try {
				res.setAdvertisedRefs(transport.getURI()
						.getRefsMap());
				res.setRemoteUpdates(toPush);
				monitor.endTask();

				final Map<String
				if (transport.isDryRun())
					modifyUpdatesForDryRun();
				else if (!preprocessed.isEmpty())
					connection.push(monitor
			} finally {
				connection.close();
				res.addMessages(connection.getMessages());
			}
			if (!transport.isDryRun())
				updateTrackingRefs();
			for (final RemoteRefUpdate rru : toPush.values()) {
				final TrackingRefUpdate tru = rru.getTrackingRefUpdate();
				if (tru != null)
					res.add(tru);
			}
			return res;
