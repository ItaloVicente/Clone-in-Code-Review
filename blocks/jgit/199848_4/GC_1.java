
	private class PidLock implements AutoCloseable {


		private final Path pidFile;

		private LockToken token;

		private FileLock lock;

		private RandomAccessFile f;

		private FileChannel channel;

		PidLock() {
			pidFile = repo.getDirectory().toPath().resolve(GC_PID);
		}

		boolean lock() {
			if (Files.exists(pidFile)) {
				Instant mtime = FS.DETECTED
						.lastModifiedInstant(pidFile.toFile());
				Instant twelveHoursAgo = Instant.now().minus(12
						ChronoUnit.HOURS);
				if (mtime.compareTo(twelveHoursAgo) > 0) {
					gcAlreadyRunning();
					return false;
				}
				LOG.warn(MessageFormat.format(JGitText.get().stalePidLock
						pidFile
			}
			try {
				token = FS.DETECTED.createNewFileAtomic(pidFile.toFile());
				f = new RandomAccessFile(pidFile.toFile()
				channel = f.getChannel();
				lock = channel.tryLock();
				if (lock == null) {
					failedToLock();
					return false;
				}
				channel.write(ByteBuffer
						.wrap(getProcDesc().getBytes(StandardCharsets.UTF_8)));
				Thread cleanupHook = new Thread(() -> close());
				try {
					Runtime.getRuntime().addShutdownHook(cleanupHook);
				} catch (IllegalStateException e) {
				}
			} catch (IOException | OverlappingFileLockException e) {
				try {
					failedToLock();
				} catch (Exception e1) {
					LOG.error(
							MessageFormat.format(
									JGitText.get().closePidLockFailed
							e1);
				}
				return false;
			}
			return true;
		}

		private void failedToLock() {
			close();
			LOG.error(MessageFormat.format(JGitText.get().failedPidLock
					pidFile));
		}

		private void gcAlreadyRunning() {
			close();
			try {
				Optional<String> s = Files.lines(pidFile).findFirst();
				String machine = null;
				String pid = null;
				if (s.isPresent()) {
					pid = c[0];
					machine = c[1];
				}
				if (!StringUtils.isEmptyOrNull(machine)
						&& !StringUtils.isEmptyOrNull(pid)) {
					LOG.error(MessageFormat.format(
							JGitText.get().gcAlreadyRunning
					return;
				}
			} catch (IOException e) {
			}
			LOG.error(MessageFormat.format(JGitText.get().failedPidLock
					pidFile));
		}

		private String getProcDesc() {
			StringBuffer s = new StringBuffer(Long.toString(getPID()));
			s.append(' ');
			s.append(getHostName());
			return s.toString();
		}

		private long getPID() {
			String processName = java.lang.management.ManagementFactory
					.getRuntimeMXBean().getName();
			if (processName != null && processName.length() > 0) {
				try {
				} catch (Exception e) {
					return 0;
				}
			}

			return 0;
		}

		private String getHostName() {
			try {
				return InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
			}
		}

		@Override
		public void close() {
			boolean wasLocked = false;
			try {
				if (lock != null) {
					lock.release();
					wasLocked = true;
				}
				if (channel != null) {
					channel.close();
				}
				if (f != null) {
					f.close();
				}
				if (token != null) {
					token.close();
				}
				if (wasLocked) {
					FileUtils.delete(pidFile.toFile()
				}
			} catch (IOException e) {
				LOG.error(MessageFormat
						.format(JGitText.get().closePidLockFailed
			}
		}

	}
