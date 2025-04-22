		super.init(site, memento);
		this.memento = memento;
	}

	protected void initDragAndDrop() {
		int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getInstance(), ResourceTransfer.getInstance(),
				FileTransfer.getInstance(), PluginTransfer.getInstance() };
		TreeViewer viewer = getTreeViewer();
		viewer.addDragSupport(ops, transfers, new NavigatorDragAdapter(viewer));
		NavigatorDropAdapter adapter = new NavigatorDropAdapter(viewer);
		adapter.setFeedbackEnabled(false);
		viewer.addDropSupport(ops | DND.DROP_DEFAULT, transfers, adapter);
		dragDetectListener = event -> dragDetected = true;
		viewer.getControl().addListener(SWT.DragDetect, dragDetectListener);
	}

	protected FrameList createFrameList() {
		NavigatorFrameSource frameSource = new NavigatorFrameSource(this);
		FrameList frameList = new FrameList(frameSource);
		frameSource.connectTo(frameList);
		return frameList;
	}

	@Deprecated
