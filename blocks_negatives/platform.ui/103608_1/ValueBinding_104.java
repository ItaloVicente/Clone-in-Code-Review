		source.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				boolean destinationRealmReached = false;
				final MultiStatus multiStatus = BindingStatus.ok();
				try {
					Object value = source.getValue();
