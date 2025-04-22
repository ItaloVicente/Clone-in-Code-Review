	private WindowCache publishMBeanIfNeeded() {
		if (publishMBean.getAndSet(false)) {
			Monitoring.registerMBean(mbean
		}
		return this;
	}

