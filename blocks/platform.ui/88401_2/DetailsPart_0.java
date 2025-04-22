					if (oldPage!=null && oldPage.isDirty())
						oldPage.commit(false);
					if (fpage.isStale())
						fpage.refresh();
					fpage.selectionChanged(masterPart, currentSelection);
					pageBook.showPage(key);
