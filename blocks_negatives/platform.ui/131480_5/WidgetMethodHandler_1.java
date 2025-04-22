		final Class swingUtilitiesClass = Class
		final Method swingUtilitiesInvokeLaterMethod = swingUtilitiesClass
				.getMethod("invokeLater", //$NON-NLS-1$
						new Class[] { Runnable.class });
		swingUtilitiesInvokeLaterMethod.invoke(swingUtilitiesClass,
				new Object[] { methodRunnable });
