			if (viewer instanceof AbstractTreeViewer) {
				((AbstractTreeViewer) viewer).add(resource, affected);
			} else {
				((StructuredViewer) viewer).refresh(resource);
			}
		}
	}

	@Override
