		static void configCreate(Properties source) throws Exception {
			Properties target = Props.discover();
			target.putAll(source);
			PrintWriter writer = new PrintWriter(JGIT_CONF_FILE);
			target.store(writer
			writer.close();
		}

