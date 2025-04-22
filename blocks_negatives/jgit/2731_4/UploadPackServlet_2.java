		} catch (ServiceNotAuthorizedException e) {
			rsp.reset();
			rsp.sendError(SC_UNAUTHORIZED);
			return;

		} catch (ServiceNotEnabledException e) {
			rsp.reset();
			rsp.sendError(SC_FORBIDDEN);
			return;

