		if (ins == null)
			ins = new FileInputStream(FileDescriptor.in);
		if (outs == null)
			outs = new FileOutputStream(FileDescriptor.out);
		if (errs == null)
			errs = new FileOutputStream(FileDescriptor.err);
		outw = new ThrowingPrintWriter(new BufferedWriter(
				new OutputStreamWriter(outs
		errw = new ThrowingPrintWriter(new BufferedWriter(
				new OutputStreamWriter(errs

