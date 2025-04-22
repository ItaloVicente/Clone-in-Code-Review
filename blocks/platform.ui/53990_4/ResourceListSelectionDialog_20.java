            display.syncExec(() -> {
			    if (resourceNames.isDisposed()) {
			        disposed[0] = true;
			        return;
			    }
			    itemCount[0] = resourceNames.getItemCount();
			});
