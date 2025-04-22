			try {
				up.uploadWithExceptionPropagation(getInputStream(req), out,
						null);
				out.close();
			} catch (ServiceMayNotContinueException e) {
				if (e.isOutput()) {
