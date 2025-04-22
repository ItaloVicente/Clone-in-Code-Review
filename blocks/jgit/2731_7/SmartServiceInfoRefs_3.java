				return;
			}

			try {
				if (filters.length == 0)
					service(req
				else
					new Chain().doFilter(request
			} finally {
				req.removeAttribute(ATTRIBUTE_HANDLER);
