				final IStructuredSelection sel = ((IStructuredSelection) s);
				if (sel.size() > 1) {
					commentViewer.setInput(null);
					fileViewer.setInput(null);
					return;
				}
				final PlotCommit<?> c = (PlotCommit<?>) sel.getFirstElement();
