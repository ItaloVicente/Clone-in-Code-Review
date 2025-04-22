		StringBuilder sb = new StringBuilder();
		TreeSet<Long> timeStamps = null;

		if (0 != (includedOptions & MOD_TIME)) {
			timeStamps = new TreeSet<Long>();
			for (int i=0; i<dc.getEntryCount(); ++i)
				timeStamps.add(Long.valueOf(dc.getEntry(i).getLastModified()));
		}

