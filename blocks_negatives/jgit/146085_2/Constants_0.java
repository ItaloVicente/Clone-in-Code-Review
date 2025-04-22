	/**
	 * Fallback filesystem timestamp resolution used when we can't measure the
	 * resolution. The last modified time granularity of FAT filesystems is 2
	 * seconds.
	 *
	 * @since 5.1.9
	 */
	public static final Duration FALLBACK_TIMESTAMP_RESOLUTION = Duration
			.ofMillis(2000);

