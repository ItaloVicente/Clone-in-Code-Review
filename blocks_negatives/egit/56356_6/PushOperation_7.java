					monitor.worked(WORK_UNITS_PER_TRANSPORT);
				} finally {
					subMonitor.beginTask("", WORK_UNITS_PER_TRANSPORT); //$NON-NLS-1$
					subMonitor.done();
					subMonitor.done();
				}
