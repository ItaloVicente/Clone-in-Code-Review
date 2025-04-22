		LargeFileRepository repo = null;
		try {
			repo = getLargeFileRepository(request
		} catch (LfsValidationError e) {
			sendError(res
			return;
		} catch (LfsRepositoryNotFound e) {
			sendError(res
			return;
		} catch (LfsRepositoryReadOnly e) {
			sendError(res
			return;
		} catch (LfsException e) {
			sendError(res
			return;
		}
