        } catch (CoreException exception) {
            handleCoreException(exception);
        }
        return null;
    }

    /**
     * Returns the decorator, or <code>null</code> if not enabled.
     *
     * @return the decorator, or <code>null</code> if not enabled
     */
    public ILabelDecorator getDecorator() {
        return decorator;
    }

    @Override
	protected IBaseLabelProvider internalGetLabelProvider()
            throws CoreException {
        return internalGetDecorator();
    }

    @Override
