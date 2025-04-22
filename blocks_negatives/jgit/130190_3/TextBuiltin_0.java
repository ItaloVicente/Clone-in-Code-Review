		try {
			final String outputEncoding = repository != null ? repository
					.getConfig().getString("i18n", null, "logOutputEncoding") : null; //$NON-NLS-1$ //$NON-NLS-2$
			if (ins == null)
				ins = new FileInputStream(FileDescriptor.in);
			if (outs == null)
				outs = new FileOutputStream(FileDescriptor.out);
			if (errs == null)
				errs = new FileOutputStream(FileDescriptor.err);
			BufferedWriter outbufw;
			if (outputEncoding != null)
				outbufw = new BufferedWriter(new OutputStreamWriter(outs,
						outputEncoding));
			else
				outbufw = new BufferedWriter(new OutputStreamWriter(outs));
			outw = new ThrowingPrintWriter(outbufw);
			BufferedWriter errbufw;
			if (outputEncoding != null)
				errbufw = new BufferedWriter(new OutputStreamWriter(errs,
						outputEncoding));
			else
				errbufw = new BufferedWriter(new OutputStreamWriter(errs));
			errw = new ThrowingPrintWriter(errbufw);
		} catch (IOException e) {
			throw die(CLIText.get().cannotCreateOutputStream);
