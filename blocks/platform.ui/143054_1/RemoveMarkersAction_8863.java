				if (message != null && message.startsWith("this is a test")) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			openError(e);
		}
	}
