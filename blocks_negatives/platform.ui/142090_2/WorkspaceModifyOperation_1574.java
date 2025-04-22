            IDEWorkbenchPlugin.getPluginWorkspace().run(workspaceRunnable,
                    rule, IResource.NONE, monitor);
        } catch (CoreException e) {
            throw new InvocationTargetException(e);
        } catch (OperationCanceledException e) {
            throw new InterruptedException(e.getMessage());
        }
        if (iteHolder[0] != null) {
            throw iteHolder[0];
        }
    }
