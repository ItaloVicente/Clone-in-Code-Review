
				for (int i = 0; i < tab.getItemCount(); i++) {
					if (!existingRepositoryDirs.contains(tv.getElementAt(i)))
						tv.setChecked(tv.getElementAt(i), !tv.getChecked(tv
								.getElementAt(i)));
				}
