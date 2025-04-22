		parent.addDisposeListener(event -> {
			for (CancelableFuture<Collection<Ref>> l : refs.values()) {
				l.cancel(CancelableFuture.CancelMode.INTERRUPT);
			}
			refs.clear();
		});
