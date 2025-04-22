			if (errs == null)
				errs = new FileOutputStream(FileDescriptor.err);
			BufferedWriter outbufw;
			if (outputEncoding != null)
				outbufw = new BufferedWriter(new OutputStreamWriter(outs
						outputEncoding));
			else
				outbufw = new BufferedWriter(new OutputStreamWriter(outs));
			out = new PrintWriter(outbufw);
			outw = new ThrowingPrintWriter(outbufw);
			BufferedWriter errbufw;
