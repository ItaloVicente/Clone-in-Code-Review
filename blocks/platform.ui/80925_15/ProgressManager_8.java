		public void removeProgresListener(IProgressMonitorWithBlocking monitor) {
			Set<IProgressMonitorWithBlocking> newSet = new LinkedHashSet<>(monitors);
			newSet.remove(monitor);
			this.monitors = Collections.unmodifiableSet(newSet);
		}

