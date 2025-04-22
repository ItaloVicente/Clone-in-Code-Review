	@SuppressWarnings({ "nls"
	private void writeStack() throws Exception {
		File dir = new File(reftablePath);
		File stackFile = new File(reftablePath + ".stack");

		dir.mkdirs();

		long start = System.currentTimeMillis();
		try (FileReftableStack stack = new FileReftableStack(stackFile
				null

			List<Ref> refs = readLsRemote().asList();
			for (Ref r : refs) {
				final long j = stack.getMergedReftable().maxUpdateIndex() + 1;
				if (!stack.addReftable(w -> {
					w.setMaxUpdateIndex(j).setMinUpdateIndex(j).begin()
							.writeRef(r);
				})) {
					throw new IOException("should succeed");
				}
			}
			long dt = System.currentTimeMillis() - start;
			printf("%12s %10d ms  avg %6d us/write"
					(dt * 1000) / refs.size());
		}
	}

