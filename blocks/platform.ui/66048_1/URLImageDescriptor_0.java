		} catch (SWTException e) {
			if (e.code != SWT.ERROR_INVALID_IMAGE) {
				throw e;
			}
		} catch (IOException e) {
			Policy.getLog().log(new Status(IStatus.ERROR, Policy.JFACE, e.getLocalizedMessage(), e));
