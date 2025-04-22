			getSite().getShell().getDisplay().syncExec(new Runnable(){
				@Override
				public void run() {
					TestBackgroundSaveEditor.this
					.firePropertyChange(ISaveablePart.PROP_DIRTY);
				}});
