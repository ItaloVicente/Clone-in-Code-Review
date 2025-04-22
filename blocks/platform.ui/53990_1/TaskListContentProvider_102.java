        viewer.getControl().getDisplay().syncExec(() -> {
		    if (getFilterOnMarkerLimit()
		            && sum(visibleMarkerCounts) > getMarkerLimit()) {
		        if (!isMarkerLimitExceeded()) {
		            setMarkerLimitExceeded(true);
		            viewer.refresh();
		        }
		    } else if (taskList.isMarkerLimitExceeded()) {
		        setMarkerLimitExceeded(false);
		        viewer.refresh();
		    } else {
		        updateViewer(additions, removals, changes);
		    }

		    taskList.markersChanged();
		});
