                    }
                }
            }

            if (disposed[0]) {
                return;
            }

            display.syncExec(() -> {
			    if (resourceNames.isDisposed()) {
			        return;
			    }
			    itemCount[0] = resourceNames.getItemCount();
			    if (itemIndex[0] < itemCount[0]) {
			        resourceNames.setRedraw(false);
			        resourceNames.remove(itemIndex[0], itemCount[0] - 1);
			        resourceNames.setRedraw(true);
			    }
			    if (resourceNames.getItemCount() == 0) {
			        folderNames.removeAll();
			        updateOKState(false);
			    }
