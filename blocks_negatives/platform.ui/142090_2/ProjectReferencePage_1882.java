                if (!(o instanceof IWorkspace)) {
                    return new Object[0];
                }

                IProject[] projects = ((IWorkspace) o).getRoot().getProjects();
                ArrayList referenced = new ArrayList(projects.length);
                boolean found = false;
                for (IProject currentProject : projects) {
                    if (!found && currentProject.equals(project)) {
                        found = true;
                        continue;
                    }
                    referenced.add(currentProject);
                }

                try {
                    projects = project.getDescription().getReferencedProjects();
                    for (int i = 0; i < projects.length; i++) {
                        if (!referenced.contains(projects[i])) {
