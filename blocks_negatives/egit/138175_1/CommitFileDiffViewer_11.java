				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<FileDiff> it = iss.iterator(); it.hasNext();) {
					FileDiff diff = it.next();
					String relativePath = diff.getPath();
