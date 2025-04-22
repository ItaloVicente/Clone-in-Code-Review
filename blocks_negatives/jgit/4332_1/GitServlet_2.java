		super.init(config);

		if (resolver == null) {
			final File root = getFile("base-path");
			final boolean exportAll = getBoolean("export-all");
			setRepositoryResolver(new FileResolver<HttpServletRequest>(root, exportAll));
		}

		initialized = true;

		if (uploadPackFactory != UploadPackFactory.DISABLED) {
			ServletBinder b = serve("*/git-upload-pack");
			b = b.through(new UploadPackServlet.Factory(uploadPackFactory));
			for (Filter f : uploadPackFilters)
				b = b.through(f);
			b.with(new UploadPackServlet());
		}

		if (receivePackFactory != ReceivePackFactory.DISABLED) {
			ServletBinder b = serve("*/git-receive-pack");
			b = b.through(new ReceivePackServlet.Factory(receivePackFactory));
			for (Filter f : receivePackFilters)
				b = b.through(f);
			b.with(new ReceivePackServlet());
		}

		ServletBinder refs = serve("*/" + Constants.INFO_REFS);
		if (uploadPackFactory != UploadPackFactory.DISABLED) {
			refs = refs.through(new UploadPackServlet.InfoRefs(
					uploadPackFactory, uploadPackFilters));
		}
		if (receivePackFactory != ReceivePackFactory.DISABLED) {
			refs = refs.through(new ReceivePackServlet.InfoRefs(
					receivePackFactory, receivePackFilters));
		}
		if (asIs != AsIsFileService.DISABLED) {
			refs = refs.through(new IsLocalFilter());
			refs = refs.through(new AsIsFileFilter(asIs));
			refs.with(new InfoRefsServlet());
		} else
			refs.with(new ErrorServlet(HttpServletResponse.SC_FORBIDDEN));

		if (asIs != AsIsFileService.DISABLED) {
			final IsLocalFilter mustBeLocal = new IsLocalFilter();
			final AsIsFileFilter enabled = new AsIsFileFilter(asIs);

					.with(new TextFileServlet(Constants.HEAD));

			final String info_alternates = "objects/info/alternates";
					.with(new TextFileServlet(info_alternates));

			final String http_alternates = "objects/info/http-alternates";
					.with(new TextFileServlet(http_alternates));

					.with(new InfoPacksServlet());

					.with(new ObjectFileServlet.Loose());

					.with(new ObjectFileServlet.Pack());

					.with(new ObjectFileServlet.PackIdx());
		}
	}

	private File getFile(final String param) throws ServletException {
		String n = getInitParameter(param);
		if (n == null || "".equals(n))
			throw new ServletException(MessageFormat.format(HttpServerText.get().parameterNotSet, param));

		File path = new File(n);
		if (!path.exists())
			throw new ServletException(MessageFormat.format(HttpServerText.get().pathForParamNotFound, path, param));
		return path;
	}

	private boolean getBoolean(String param) throws ServletException {
		String n = getInitParameter(param);
		if (n == null)
			return false;
		try {
			return StringUtils.toBoolean(n);
		} catch (IllegalArgumentException err) {
			throw new ServletException(MessageFormat.format(HttpServerText.get().invalidBoolean, param, n));
		}
	}

	@Override
	protected ServletBinder register(ServletBinder binder) {
		if (resolver == null)
			throw new IllegalStateException(HttpServerText.get().noResolverAvailable);
		binder = binder.through(new NoCacheFilter());
		binder = binder.through(new RepositoryFilter(resolver));
		return binder;
