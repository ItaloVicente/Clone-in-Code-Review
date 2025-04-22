
		IProgressMonitor monitor = new NullProgressMonitor();
		f1.delete(false, monitor);
		f2.delete(false, monitor);
		f3.delete(false, monitor);
		p.getProject().getFolder("test").delete(false, monitor);
		p.getProject().delete(false, monitor);
