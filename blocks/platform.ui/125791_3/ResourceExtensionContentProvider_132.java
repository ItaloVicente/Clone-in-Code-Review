			ctrl.getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					Control ctrl = viewer.getControl();
					if (ctrl == null || ctrl.isDisposed()) {
						return;
					}
