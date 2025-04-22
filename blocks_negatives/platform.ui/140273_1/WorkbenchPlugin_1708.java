            return ret[0];

        } catch (CoreException core) {
            throw core;
        } catch (Exception e) {
            throw new CoreException(new Status(IStatus.ERROR, PI_WORKBENCH,
                    IStatus.ERROR, WorkbenchMessages.WorkbenchPlugin_extension,e));
        }
    }

    /**
	 * Answers whether the provided element either has an attribute with the
	 * given name or a child element with the given name with an attribute
	 * called class.
