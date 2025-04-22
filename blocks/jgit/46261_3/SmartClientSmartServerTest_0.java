		ServletContextHandler redirected = server.addContext("/redirect");
		redirected.addServlet(new ServletHolder(new HttpServlet() {

			@Override
			protected void doPost(HttpServletRequest req
					HttpServletResponse resp)
							throws ServletException
				redirectToGit(req
			}

			@Override
			protected void doPut(HttpServletRequest req
					HttpServletResponse resp)
							throws ServletException
				redirectToGit(req
			}

			@Override
			protected void doGet(HttpServletRequest req
					HttpServletResponse resp)
							throws ServletException
				redirectToGit(req
			}

			protected void redirectToGit(HttpServletRequest req
					HttpServletResponse resp) throws IOException {
				String location = "/git" + req.getPathInfo();
				Map<String
				for (String k : p.keySet()) {
					for (String v : p.get(k)) {
						resp.addHeader(k
					}
				}
				resp.sendRedirect(location);
			}

		})

