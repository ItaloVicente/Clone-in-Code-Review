				BusyIndicator.showWhile(pageBook.getDisplay(), new Runnable() {
					@Override
					public void run() {
						if (!pageBook.hasPage(key)) {
							Composite parent = pageBook.createPage(key);
							fpage.createContents(parent);
							parent.setData(fpage);
						}
						if (oldPage!=null && oldPage.isDirty())
							oldPage.commit(false);
						if (fpage.isStale())
							fpage.refresh();
						fpage.selectionChanged(masterPart, currentSelection);
						pageBook.showPage(key);
