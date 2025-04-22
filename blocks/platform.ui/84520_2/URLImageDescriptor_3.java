				return new BufferedInputStream(url.openStream());
			} catch (IOException e) {
				if (InternalPolicy.DEBUG_LOG_URL_IMAGE_DESCRIPTOR_MISSING_2x) {
					String path = url.getPath();
					if (path.endsWith("@2x.png") || path.endsWith("@1.5x.png")) { //$NON-NLS-1$ //$NON-NLS-2$
						String message = "High-resolution image missing: " + url; //$NON-NLS-1$
						Policy.getLog().log(new Status(IStatus.WARNING, Policy.JFACE, message, e));
					}
