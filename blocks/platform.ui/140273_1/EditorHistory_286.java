			if (!"".equals(item.getName()) || !"".equals(item.getToolTipText())) { //$NON-NLS-1$ //$NON-NLS-2$
				add(item, fifoList.size());
			}
		}
		return Status.OK_STATUS;
	}
