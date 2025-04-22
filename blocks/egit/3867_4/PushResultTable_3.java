		final IStyledLabelProvider styleProvider = new WorkbenchStyledLabelProvider() {

			public StyledString getStyledText(Object element) {
				if (element instanceof RefUpdateElement)
					return ((RefUpdateElement) element).getStyledText(element);
				if (element instanceof RepositoryCommit)
					return ((RepositoryCommit) element).getStyledText(element);

				return super.getStyledText(element);
			}

		};

		treeViewer.setSorter(new ViewerSorter() {

			public int compare(Viewer viewer, Object e1, Object e2) {

				if (e1 instanceof RefUpdateElement
						&& e2 instanceof RefUpdateElement) {
					RefUpdateElement r1 = (RefUpdateElement) e1;
					RefUpdateElement r2 = (RefUpdateElement) e2;

					if (r1.isRejected() && !r2.isRejected())
						return -1;
					if (!r1.isRejected() && r2.isRejected())
						return 1;

					if (r1.isAdd() && !r2.isAdd())
						return -1;
					if (!r1.isAdd() && r2.isAdd())
						return 1;

					if (!r1.isTag() && r2.isTag())
						return -1;
					if (r1.isTag() && !r2.isTag())
						return 1;

					Status s1 = r1.getStatus();
					Status s2 = r2.getStatus();
					if (s1 != Status.UP_TO_DATE && s2 == Status.UP_TO_DATE)
						return -1;
					if (s1 == Status.UP_TO_DATE && s2 != Status.UP_TO_DATE)
						return 1;

					String ref1 = r1.getDstRefName();
					String ref2 = r2.getDstRefName();
					if (ref1 != null && ref2 != null)
						return ref1.compareToIgnoreCase(ref2);
				}

				if (e1 instanceof RepositoryCommit
						&& e2 instanceof RepositoryCommit)
					return 0;

				return super.compare(viewer, e1, e2);
			}

		});
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
				styleProvider));
		treeViewer.setContentProvider(new RefUpdateContentProvider());
