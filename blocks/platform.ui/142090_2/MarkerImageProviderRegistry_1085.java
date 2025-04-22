					} else if (desc.imageDescriptor != null) {
						return desc.imageDescriptor;
					}
				}
			} catch (CoreException e) {
				Policy.handle(e);
				return null;
			}
		}
		return null;
	}

	ImageDescriptor getImageDescriptor(Descriptor desc) {
