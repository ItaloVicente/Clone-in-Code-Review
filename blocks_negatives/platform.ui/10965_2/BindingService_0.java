		if (bp == null) {
			bp = new BindingPersistence(manager, commandManager) {
				@Override
				public void reRead() {
					super.reRead();
					persistToModel(manager.getActiveScheme());
				}
			};
		}
