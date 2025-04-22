			private String getTraceMessage(long time) {
				return NLS
						.bind("\nUpdated IndexDiffData in {0} ms\nReason: {1}\nRepository: {2}\n", //$NON-NLS-1$
						new Object[] { new Long(time), trigger,
								repository.getWorkTree().getName() });
			}

