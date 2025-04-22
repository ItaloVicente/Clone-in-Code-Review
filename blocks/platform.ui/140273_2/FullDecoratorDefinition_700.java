		} catch (CoreException exception) {
			handleCoreException(exception);
		}
		return null;
	}

	public ILabelDecorator getDecorator() {
		return decorator;
	}

	@Override
	protected IBaseLabelProvider internalGetLabelProvider() throws CoreException {
		return internalGetDecorator();
	}

	@Override
