		} catch (FileNotFoundException e) {
			throw new WorkbenchException(new Status(IStatus.ERROR,
					TestPlugin.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, e.toString(), e));
		} catch (IOException e) {
			throw new WorkbenchException(new Status(IStatus.ERROR,
					TestPlugin.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, e.toString(), e));
		}
	}
