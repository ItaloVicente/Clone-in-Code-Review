				if (listenToDeactivate && event.widget == getShell()
						&& getShell().getShells().length == 0) {
					asyncClose();
				} else {
					/*
					 * We typically ignore deactivates to work around
					 * platform-specific event ordering. Now that we've ignored
					 * whatever we were supposed to, start listening to
					 * deactivates. Example issues can be found in
					 */
					listenToDeactivate = true;
				}
