			throw new CoreException(Activator.error(NLS.bind(CoreText.GitProjectData_saveFailed,
					dat), ioe));
		}

		dat.delete();
		if (!tmp.renameTo(dat)) {
			tmp.delete();
			throw new CoreException(Activator.error(NLS.bind(CoreText.GitProjectData_saveFailed,
					dat), null));
