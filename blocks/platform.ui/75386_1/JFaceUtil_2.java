		InternalPolicy.setCancelabilityMonitorOptions(new CancelabilityMonitor.ForwardingOptions() {
			private Options defaultOptions;
			@Override
			protected Options delegate() {
				Options optionService = Activator.getDefault().getCancelabilityMonitorOptions();
				if (optionService == null) {
					if (defaultOptions == null) {
						defaultOptions = new CancelabilityMonitor.BasicOptionsImpl();
						((BasicOptionsImpl) defaultOptions).setEnabled(false);
					}
					return defaultOptions;
				}
				return optionService;
			}
		});

