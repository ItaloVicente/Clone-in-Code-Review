            pageLink.addSelectionListener(new SelectionAdapter() {
                @Override
				public void widgetSelected(SelectionEvent e) {
                    pageContainer.openPage(pageId, pageData);
                }
            });
