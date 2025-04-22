            } else {
                hasClosedProjects = true;
                hasBuilder = false;
            }
        }
        if (!selection.isEmpty() && isProjectSelection
                && !ResourcesPlugin.getWorkspace().isAutoBuilding()
                && hasBuilder) {
            buildAction.selectionChanged(selection);
            menu.add(buildAction);
        }
        if (!hasClosedProjects) {
            refreshAction.selectionChanged(selection);
            menu.add(refreshAction);
        }
        if (isProjectSelection) {
            if (hasClosedProjects) {
                openProjectAction.selectionChanged(selection);
                menu.add(openProjectAction);
            }
            if (hasOpenProjects) {
                closeProjectAction.selectionChanged(selection);
                menu.add(closeProjectAction);
                closeUnrelatedProjectsAction.selectionChanged(selection);
                menu.add(closeUnrelatedProjectsAction);
            }
        }
    }
