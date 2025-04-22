		resizing = false;

		if (trace) {
			long stop = System.currentTimeMillis();
			long time = stop - start;
			long lps = (lines * 1000) / (time + 1);
			System.out
					.println("Resize + diff: " + time + " ms, line/s: " + lps); //$NON-NLS-1$ //$NON-NLS-2$
		}
