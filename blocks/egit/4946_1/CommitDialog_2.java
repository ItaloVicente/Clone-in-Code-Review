				if (!preselectAll && !preselectedFiles.contains(item.path))
					continue;
				if (item.status == Status.ASSUME_UNCHANGED)
					continue;
				if (!includeUntracked && item.status == Status.UNTRACKED)
					continue;
				filesViewer.setChecked(item, true);
