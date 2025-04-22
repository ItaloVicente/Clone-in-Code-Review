
		} catch (ServiceNotAuthorizedException e) {
			rsp.sendError(SC_UNAUTHORIZED);
			return;

		} catch (ServiceNotEnabledException e) {
			rsp.sendError(SC_FORBIDDEN);
			return;

