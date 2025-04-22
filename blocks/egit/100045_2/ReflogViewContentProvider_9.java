			loader.addUpdateCompleteListener(new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (event.getResult().isOK()) {
						viewer.setSelection(viewer.getSelection());
					}
				}
			});
