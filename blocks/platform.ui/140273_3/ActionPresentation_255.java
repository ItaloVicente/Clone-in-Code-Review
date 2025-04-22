		public SetRec(IActionSet set, SubActionBars bars) {
			this.set = set;
			this.bars = bars;
		}

		public IActionSet set;

		public SubActionBars bars;
	}

	public ActionPresentation(WorkbenchWindow window) {
		super();
		this.window = window;
	}

	public void clearActionSets() {
		final List oldList = new ArrayList();
		oldList.addAll(mapDescToRec.keySet());
		oldList.addAll(invisibleBars.keySet());

		Iterator iter = oldList.iterator();
		while (iter.hasNext()) {
			IActionSetDescriptor desc = (IActionSetDescriptor) iter.next();
			removeActionSet(desc);
		}
	}

	public void removeActionSet(IActionSetDescriptor desc) {
		SetRec rec = (SetRec) mapDescToRec.remove(desc);
		if (rec == null) {
			rec = (SetRec) invisibleBars.remove(desc);
		}
		if (rec != null) {
			IActionSet set = rec.set;
			SubActionBars bars = rec.bars;
			if (bars != null) {
				bars.dispose();
			}
			if (set != null) {
				set.dispose();
			}
		}
	}

	public void setActionSets(IActionSetDescriptor[] newArray) {
		HashSet newList = new HashSet();

		for (IActionSetDescriptor descriptor : newArray) {
			newList.add(descriptor);
		}
		List oldList = new ArrayList(mapDescToRec.keySet());

		Iterator iter = oldList.iterator();
		while (iter.hasNext()) {
			IActionSetDescriptor desc = (IActionSetDescriptor) iter.next();
			if (!newList.contains(desc)) {
				SetRec rec = (SetRec) mapDescToRec.get(desc);
				if (rec != null) {
					mapDescToRec.remove(desc);
					IActionSet set = rec.set;
					SubActionBars bars = rec.bars;
					if (bars != null) {
						SetRec invisibleRec = new SetRec(set, bars);
						invisibleBars.put(desc, invisibleRec);
						bars.deactivate();
					}
				}
			}
		}

		ArrayList sets = new ArrayList();
