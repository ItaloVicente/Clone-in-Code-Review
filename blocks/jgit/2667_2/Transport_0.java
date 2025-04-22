
		registerByService();
	}

	private static void registerByService() {
		ClassLoader ldr = Thread.currentThread().getContextClassLoader();
		if (ldr == null)
			ldr = Transport.class.getClassLoader();
		Enumeration<URL> catalogs = catalogs(ldr);
		while (catalogs.hasMoreElements())
			scan(ldr
	}

	private static Enumeration<URL> catalogs(ClassLoader ldr) {
		try {
			String prefix = "META-INF/services/";
			String name = prefix + Transport.class.getName();
			return ldr.getResources(name);
		} catch (IOException err) {
			return new Vector<URL>().elements();
		}
	}

	private static void scan(ClassLoader ldr
		BufferedReader br;
		try {
			InputStream urlIn = url.openStream();
			br = new BufferedReader(new InputStreamReader(urlIn
		} catch (IOException err) {
			return;
		}

		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0 && !line.startsWith("#"))
					load(ldr
			}
		} catch (IOException err) {
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}

	private static void load(ClassLoader ldr
		Class<?> clazz;
		try {
			clazz = Class.forName(cn
		} catch (ClassNotFoundException notBuiltin) {
			return;
		}

		for (Field f : clazz.getDeclaredFields()) {
			if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC
					&& TransportProtocol.class.isAssignableFrom(f.getType())) {
				TransportProtocol proto;
				try {
					proto = (TransportProtocol) f.get(null);
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}
				if (proto != null)
					register(proto);
			}
		}
