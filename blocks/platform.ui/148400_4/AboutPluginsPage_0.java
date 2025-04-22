			vendorInfo.getTable().setRedraw(false);
			try {
				vendorInfo.refresh();
			} finally {
				vendorInfo.getTable().setRedraw(true);
			}
