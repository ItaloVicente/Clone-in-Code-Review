		parent.addDisposeListener(event -> {
			for (ChangeList l : changeRefs.values()) {
				l.cancel(ChangeList.CancelMode.INTERRUPT);
			}
			changeRefs.clear();
		});
