			FileUtils.delete(dat);
			if (!tmp.renameTo(dat)) {
				FileUtils.delete(tmp);
				throw new CoreException(
						Activator.error(NLS.bind(
								CoreText.GitProjectData_saveFailed, dat), null));
			}
