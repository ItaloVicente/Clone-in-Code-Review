		} catch (IOException e) {
			WorkbenchNavigatorPlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.getDefault().getBundle().getSymbolicName(),
							"Failed to import " + folder.getName(), e)); //$NON-NLS-1$
		} catch (ExecutionException e) {
			WorkbenchNavigatorPlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.getDefault().getBundle().getSymbolicName(),
							"Failed to import " + folder.getName(), e)); //$NON-NLS-1$
