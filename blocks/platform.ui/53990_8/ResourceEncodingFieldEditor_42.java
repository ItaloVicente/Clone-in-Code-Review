		Job charsetJob = Job.create(IDEWorkbenchMessages.IDEEncoding_EncodingJob, monitor -> {
			try {
				if (!hasSameEncoding) {
					if (resource instanceof IContainer) {
						((IContainer) resource).setDefaultCharset(
								finalEncoding, monitor);
					} else {
						((IFile) resource).setCharset(finalEncoding,
								monitor);
