            try {
                monitor.beginTask(IDEWorkbenchMessages.CleanDialog_cleanSelectedTaskName, selection.length);
                for (int i = 0; i < selection.length; i++) {
                    ((IProject) selection[i]).build(
                            IncrementalProjectBuilder.CLEAN_BUILD,
                            new SubProgressMonitor(monitor, 1));
                }
            } finally {
                monitor.done();
