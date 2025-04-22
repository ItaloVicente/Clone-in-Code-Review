		public void removeProgresListener(IProgressMonitorWithBlocking monitor) {
			Set<IProgressMonitorWithBlocking> newSet = new HashSet<>(monitors);
			newSet.remove(monitor);
			this.monitors = Collections.unmodifiableSet(newSet);
		}

