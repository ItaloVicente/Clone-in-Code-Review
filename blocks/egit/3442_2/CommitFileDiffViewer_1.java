				public void focusGained(FocusEvent e) {
					updateActionEnablement(getSelection());
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.SELECT_ALL.getId(), selectAll);
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.COPY.getId(), copy);
					pageSite.getActionBars().updateActionBars();
				}
			});
		}
