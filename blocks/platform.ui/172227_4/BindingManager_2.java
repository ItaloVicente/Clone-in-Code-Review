						conflicts.add(new Status(IStatus.WARNING,
								"org.eclipse.jface", //$NON-NLS-1$
								sw.toString()));
					}
					if (DEBUG) {
						Tracing.printTrace("BINDINGS", //$NON-NLS-1$
								"A conflict occurred for " + trigger); //$NON-NLS-1$
						Tracing.printTrace("BINDINGS", "    " + match); //$NON-NLS-1$ //$NON-NLS-2$
