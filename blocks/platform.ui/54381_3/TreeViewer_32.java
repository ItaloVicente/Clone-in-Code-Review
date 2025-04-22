			}
			if (removedPath != null) {
				boolean removed = false;
				for (Iterator it = oldSelection.iterator(); it
						.hasNext();) {
					TreePath path = (TreePath) it.next();
					if (path.startsWith(removedPath, getComparer())) {
						it.remove();
						removed = true;
