public abstract class VersionAttributeViewImpl<P extends Path> extends AbstractBasicFileAttributeView<P>
		implements VersionAttributeView {

	public static final String VERSION = "version";

	public VersionAttributeViewImpl(final P path) {
		super(path);
	}

	@Override
	public String name() {
		return VERSION;
	}

	@Override
	public Map<String
		final VersionAttributes attrs = readAttributes();

		return new HashMap<String
			{
				for (final String attribute : attributes) {
					checkNotEmpty("attribute"

					if (attribute.equals("*") || attribute.equals(VERSION)) {
						put(VERSION
					}

					if (attribute.equals("*")) {
						break;
					}
				}
			}
		};
	}
