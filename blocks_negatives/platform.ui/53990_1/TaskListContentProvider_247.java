        viewer.getControl().getDisplay().syncExec(new Runnable() {
            @Override
			public void run() {
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

                /* Update the task list's status message.
                 * XXX: Quick and dirty solution here.
                 * Would be better to have a separate model for the tasks and
                 * have both the content provider and the task list register for
                 * updates. XXX: Do this inside the syncExec, since we're
                 * talking to status line widget.
                 */
                taskList.markersChanged();
            }
        });
