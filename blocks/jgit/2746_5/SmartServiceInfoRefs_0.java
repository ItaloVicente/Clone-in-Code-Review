
		} catch (UploadPackMayNotContinueException e) {
			if (e.isOutput())
				buf.close();
			else
				rsp.sendError(SC_SERVICE_UNAVAILABLE);
