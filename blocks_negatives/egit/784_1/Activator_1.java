				monitor.done();
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(),
				if (doReschedule)
					schedule(REPO_SCAN_INTERVAL);
			} catch (Exception e) {
