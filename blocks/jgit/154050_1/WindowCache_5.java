		mbean = new StatsRecorderImpl();
		statsRecorder = mbean;
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName mbeanName = new ObjectName(
			if (server.isRegistered(mbeanName)) {
				server.unregisterMBean(mbeanName);
			}
			server.registerMBean(mbean
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException
				| MBeanRegistrationException | NotCompliantMBeanException
				| InstanceNotFoundException e) {
			System.err.println(e);
		}
