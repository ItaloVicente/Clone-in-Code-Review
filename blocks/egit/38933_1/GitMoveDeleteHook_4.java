		} catch (LockFailedException e) {
			Activator.getDefault().getLog()
					.log(new Status(IStatus.WARNING, Activator.getPluginId(),
							MessageFormat
									.format(CoreText.MoveDeleteHook_cannotAutoStageDeletion,
											file.getLocation())));
			return FINISH_FOR_ME;
