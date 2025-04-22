			DisplayRealm.getRealm(display).exec(new Runnable() {
				@Override
				public void run() {
					if (!widget.isDisposed())
						widget.addListener(event, listener);
				}
