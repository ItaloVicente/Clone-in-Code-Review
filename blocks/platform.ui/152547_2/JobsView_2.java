					monitor -> {
if (shouldLock)
					doRunInWorkspace(duration, monitor);
else
					doRun(duration, monitor);
}, ResourcesPlugin.getWorkspace().getRoot());
