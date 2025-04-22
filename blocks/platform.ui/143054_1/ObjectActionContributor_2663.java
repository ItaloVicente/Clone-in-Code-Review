				} catch (CoreException e) {
					enablement = null;
					WorkbenchPlugin.log(e);
					result = false;
				}
			}
			return result;
		}
	}

	@Override
