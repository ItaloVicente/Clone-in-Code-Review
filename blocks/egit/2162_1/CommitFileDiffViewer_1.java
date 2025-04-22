		openWorkingTree = new Action(UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<FileDiff> it = iss.iterator(); it.hasNext();) {
					String relativePath = it.next().getPath();
					String path = new Path(db.getWorkTree().getAbsolutePath())
							.append(relativePath).toOSString();
					openFileInEditor(path);
				}
			}
		};

