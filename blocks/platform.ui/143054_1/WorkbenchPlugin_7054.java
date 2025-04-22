			return ret[0];

		} catch (CoreException core) {
			throw core;
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, PI_WORKBENCH, IStatus.ERROR,
					WorkbenchMessages.WorkbenchPlugin_extension, e));
		}
	}

