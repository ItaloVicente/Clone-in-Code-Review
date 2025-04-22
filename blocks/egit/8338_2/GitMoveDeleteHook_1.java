			if (sEnt == null)
				return FINISH_FOR_ME;

			if (!sEnt.isMerged()) {
				tree.failed(new Status(IStatus.WARNING, Activator.getPluginId(),
						CoreText.MoveDeleteHook_unmergedFileError));
				return I_AM_DONE;
