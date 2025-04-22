								SubMonitor monitor = null;
								if (aMonitor instanceof SubMonitor) {
									monitor = (SubMonitor) aMonitor;
								} else {
									monitor = SubMonitor.convert(aMonitor);
								}
