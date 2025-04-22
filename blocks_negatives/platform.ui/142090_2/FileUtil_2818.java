    /**
     * Deletes a project.
     *
     * @param proj the project
     */
    public static void deleteProject(IProject proj) throws CoreException {
        proj.delete(true, null);
    }
