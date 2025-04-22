
				for (IKeyBindingDispatcherInterceptor interceptor : fInterceptors) {
					if (interceptor.interceptExecutePerfectMatch(cmd, event)) {
						return false;
					}
				}
