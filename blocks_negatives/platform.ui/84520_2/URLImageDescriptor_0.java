			}
			return new BufferedInputStream(url.openStream());
		} catch (IOException e) {
			if (InternalPolicy.DEBUG_LOG_URL_IMAGE_DESCRIPTOR_MISSING_2x) {
				String path = url.getPath();
					Policy.getLog().log(new Status(IStatus.WARNING, Policy.JFACE, message, e));
