                } catch (CoreException e) {
                    enablement = null;
                    WorkbenchPlugin.log(e);
                    result = false;
                }
            }
            return result;
        }
    }

    /**
     * Debugging helper that will print out the contribution names for this
     * contributor.
     */
    @Override
