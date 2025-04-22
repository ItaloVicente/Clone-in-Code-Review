				ISelection s = event.getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				commitToShow = (PlotCommit<?>) iss.getFirstElement();

