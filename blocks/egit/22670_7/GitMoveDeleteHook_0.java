		} catch (LockFailedException e) {
			Activator
					.getDefault()
					.getLog()
					.log(new Status(IStatus.WARNING, Activator.getPluginId(),
							CoreText.MoveDeleteHook_cannotAutoStageDeletion));
			return FINISH_FOR_ME;
