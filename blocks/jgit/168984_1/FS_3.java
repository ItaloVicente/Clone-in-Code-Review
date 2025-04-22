		private static Duration getFsResolution(FileStore s
				Path probe) throws IOException {
			File probeFile = probe.toFile();
			FileTime t1 = Files.getLastModifiedTime(probe);
			Instant t1i = t1.toInstant();
			FileTime t2;
			Duration last = FALLBACK_TIMESTAMP_RESOLUTION;
			long minScale = MINIMUM_RESOLUTION_NANOS;
			long scale = ONE_SECOND;
			long high = TimeUnit.MILLISECONDS.toSeconds(last.toMillis());
			long low = 0;
			long[] tries = { ONE_MICROSECOND
			for (long interval : tries) {
				if (interval >= ONE_MILLISECOND) {
					probeFile.setLastModified(
							t1i.plusNanos(interval).toEpochMilli());
				} else {
					Files.setLastModifiedTime(probe
							FileTime.from(t1i.plusNanos(interval)));
				}
				t2 = Files.getLastModifiedTime(probe);
				if (t2.compareTo(t1) > 0) {
					Duration diff = Duration.between(t1i
					if (!diff.isZero() && !diff.isNegative()
							&& diff.compareTo(last) < 0) {
						scale = interval;
						high = 1;
						last = diff;
						break;
					}
				} else {
					minScale = Math.max(minScale
				}
			}
			while (high > low) {
				long mid = (high + low) / 2;
				if (mid == 0) {
					long newScale = scale / 10;
					if (newScale < minScale) {
						break;
					}
					high *= scale / newScale;
					low *= scale / newScale;
					scale = newScale;
					mid = (high + low) / 2;
				}
				long delta = mid * scale;
				if (scale >= ONE_MILLISECOND) {
					probeFile.setLastModified(
							t1i.plusNanos(delta).toEpochMilli());
				} else {
					Files.setLastModifiedTime(probe
							FileTime.from(t1i.plusNanos(delta)));
				}
				t2 = Files.getLastModifiedTime(probe);
				int cmp = t2.compareTo(t1);
				if (cmp > 0) {
					high = mid;
					Duration diff = Duration.between(t1i
					if (diff.isZero() || diff.isNegative()) {
						LOG.warn(JGitText.get().logInconsistentFiletimeDiff
								Thread.currentThread()
								last);
						break;
					} else if (diff.compareTo(last) > 0) {
						LOG.warn(JGitText.get().logLargerFiletimeDiff
								Thread.currentThread()
						break;
					}
					last = diff;
				} else if (cmp < 0) {
					LOG.warn(JGitText.get().logSmallerFiletime
							Thread.currentThread()
					break;
				} else {
					low = mid + 1;
				}
			}
			return last;
		}

