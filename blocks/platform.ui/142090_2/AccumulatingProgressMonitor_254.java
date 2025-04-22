		}
	}

	public AccumulatingProgressMonitor(IProgressMonitor monitor, Display display) {
		super(monitor);
		Assert.isNotNull(display);
		this.display = display;
	}

	@Override
