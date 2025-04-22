		elementSetFactory = target -> {
			IElementComparer comparer = null;
			if (target instanceof StructuredViewer)
				comparer = ((StructuredViewer) target).getComparer();
			return ObservableViewerElementSet.withComparer(DisplayRealm
					.getRealm(display), null, comparer);
