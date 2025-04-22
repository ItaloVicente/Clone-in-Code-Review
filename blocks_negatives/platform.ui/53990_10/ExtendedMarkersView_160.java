		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if(selection instanceof ITreeSelection) {
					ITreeSelection ss = (ITreeSelection) selection;
					if(ss.size() == 1) {
						Object obj = ss.getFirstElement();
						if(viewer.isExpandable(obj)) {
							viewer.setExpandedState(obj, !viewer.getExpandedState(obj));
						}
