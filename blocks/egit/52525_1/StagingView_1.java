	@Override
	public void init(IViewSite site, IMemento viewMemento)
			throws PartInitException {
		super.init(site, viewMemento);
		this.memento = viewMemento;
	}

