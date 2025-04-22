
				for (IKeyBindingInterceptor interceptor : fInterceptors) {
					if (interceptor.executeCommand(cmd, event)) {
						return false;
					}
				}
