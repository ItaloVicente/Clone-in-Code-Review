		String info = req.getPathInfo();
		if (info.length() != 1 + Constants.LONG_OBJECT_ID_STRING_LENGTH) {
			sendError(rsp, HttpStatus.SC_UNPROCESSABLE_ENTITY, MessageFormat
					.format(LfsServerText.get().invalidPathInfo, info));
			return null;
		}
