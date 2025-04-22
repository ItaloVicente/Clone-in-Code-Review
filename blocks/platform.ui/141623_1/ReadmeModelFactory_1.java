		    for (IConfigurationElement configElement : configElements) {
			IConfigurationElement config = configElements[i];
			if (config.getName()
				.equals(IReadmeConstants.TAG_PARSER)) {
			    processParserElement(config);
			    break;
			}
		    }
