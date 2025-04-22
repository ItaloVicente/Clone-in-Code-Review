	private static final Logger LOG = LoggerFactory
			.getLogger(FileWindowReaderFactory.class);

	private volatile static Constructor<?> panamaImplCtor = getPanamaImplCtor();

	private static Constructor<?> getPanamaImplCtor() {
		if (panamaImplCtor == null) {
			if (Runtime.version().feature() >= 18) {
				try {
					Class<?> clazz = Class.forName(
					if (clazz != null) {
						panamaImplCtor = clazz.getConstructor(Pack.class);
					}
				} catch (ClassNotFoundException | NoSuchMethodException
						| SecurityException e) {
					LOG.debug(e.getMessage()
				}
			}
		}
		return panamaImplCtor;
	}

	private static FileWindowReader createPanamaFileWindowReader(Pack pack) {
		if (panamaImplCtor != null) {
			try {
				return (FileWindowReader) panamaImplCtor.newInstance(pack);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				LOG.error(e.getMessage()
			}
		}
		return null;
	}

