	private boolean packIsCoalesceableGarbage(DfsPackDescription d

		if (d.getPackSource() != UNREACHABLE_GARBAGE
				|| d.getFileSize(PackExt.PACK) >= coalesceGarbageLimit) {
			return false;
		}

		if (garbageTtlMillis == 0) {
			return true;
		}

		long lastModified = d.getLastModified();
		long dayStartLastModified = dayStartInMillis(lastModified);
		long dayStartToday = dayStartInMillis(now);

		if (dayStartLastModified != dayStartToday) {
		}

		if (garbageTtlMillis > TimeUnit.DAYS.toMillis(1)) {
		}

		long timeInterval = garbageTtlMillis / 3;
		if (timeInterval == 0) {
		}

		long modifiedTimeSlot = (lastModified - dayStartLastModified) / timeInterval;
		long presentTimeSlot = (now - dayStartToday) / timeInterval;
		return modifiedTimeSlot == presentTimeSlot;
	}

	private static long dayStartInMillis(long timeInMillis) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(timeInMillis);
		cal.set(Calendar.HOUR_OF_DAY
		cal.set(Calendar.MINUTE
		cal.set(Calendar.SECOND
		cal.set(Calendar.MILLISECOND
		return cal.getTimeInMillis();
	}

