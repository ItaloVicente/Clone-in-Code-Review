		Job charsetJob = Job.create(IDEWorkbenchMessages.IDEEncoding_EncodingJob, new IJobFunction() {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				try {
					if (!hasSameEncoding) {
						if (resource instanceof IContainer) {
							((IContainer) resource).setDefaultCharset(
									finalEncoding, monitor);
						} else {
							((IFile) resource).setCharset(finalEncoding,
									monitor);
						}
					}
					if (!hasSameSeparateDerivedEncodings) {
						Preferences prefs = new ProjectScope((IProject) resource).getNode(ResourcesPlugin.PI_RESOURCES);
						boolean newValue = !getStoredSeparateDerivedEncodingsValue();
						if (newValue == DEFAULT_PREF_SEPARATE_DERIVED_ENCODINGS)
							prefs.remove(ResourcesPlugin.PREF_SEPARATE_DERIVED_ENCODINGS);
						else
							prefs.putBoolean(ResourcesPlugin.PREF_SEPARATE_DERIVED_ENCODINGS, newValue);
						prefs.flush();
