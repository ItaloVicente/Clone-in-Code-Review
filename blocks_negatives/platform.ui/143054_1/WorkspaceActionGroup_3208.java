            if (project == null) {
                isProjectSelection = false;
                continue;
            }
            if (project.isOpen()) {
                hasOpenProjects = true;
                if (hasBuilder && !hasBuilder(project)) {
