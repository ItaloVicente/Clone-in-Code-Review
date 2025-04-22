			this.activateElement = new QuickAccessElement[] { new QuickAccessElement() {
				@Override
				public String getLabel() {
					return NLS.bind(QuickAccessMessages.QuickAccessContents_activate,
							QuickAccessProviderExtensionProxy.this.getName());
				}

				@Override
				public ImageDescriptor getImageDescriptor() {
					return null;
				}

				@Override
				public String getId() {
					return "activate-" + QuickAccessProviderExtensionProxy.this.getId(); //$NON-NLS-1$
				}

				@Override
				public void execute() {
					try {
						bundle.start();
						reset();
						if (onActivate != null) {
							onActivate.run();
						}
					} catch (BundleException e) {
						WorkbenchPlugin.log(e);
					}
				}
			} };
