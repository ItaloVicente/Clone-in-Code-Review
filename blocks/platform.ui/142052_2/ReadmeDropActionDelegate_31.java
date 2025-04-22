		if (source instanceof byte[] && target instanceof IFile) {
			IFile file = (IFile) target;
			try {
				file.appendContents(new ByteArrayInputStream((byte[]) source), false, true, null);
			} catch (CoreException e) {
				System.out.println(
						MessageUtil.getString("Exception_in_readme_drop_adapter") + e.getStatus().getMessage()); //$NON-NLS-1$
				return false;
			}
			return true;
		}
		return false;
	}
