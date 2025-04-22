				ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection)) {
					return;
				}
				IStructuredSelection iss = (IStructuredSelection) s;
				FileDiff d = (FileDiff) iss.getFirstElement();
				showTwoWayFileDiff(d);
