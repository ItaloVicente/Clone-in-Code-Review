		viewer.addDoubleClickListener(event -> {
			ISelection selection = event.getSelection();
			if(selection instanceof ITreeSelection) {
				ITreeSelection ss = (ITreeSelection) selection;
				if(ss.size() == 1) {
					Object obj = ss.getFirstElement();
					if(viewer.isExpandable(obj)) {
						viewer.setExpandedState(obj, !viewer.getExpandedState(obj));
