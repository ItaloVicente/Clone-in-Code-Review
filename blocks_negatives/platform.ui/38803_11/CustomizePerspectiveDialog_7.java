						text = (String) name;
					} else {
						text = getToolbarLabel(toolBar.getElementId());
					}
					DisplayItem toolBarEntry = new DisplayItem(text, contributionItem);
					toolBarEntry.setImageDescriptor(toolbarImageDescriptor);
					toolBarEntry.setActionSet(idToActionSet.get(getActionSetID(toolBar)));
					root.addChild(toolBarEntry);
					toolBarEntry.setCheckState(getToolbarItemIsVisible(toolBarEntry));
					createToolbarEntries(toolBar, toolBarEntry);
