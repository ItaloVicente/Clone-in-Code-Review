public abstract class DiffAttributeViewImpl<P extends Path>
        extends AbstractBasicFileAttributeView<P> implements DiffAttributeView {

    public static final String DIFF = "diff";

    public DiffAttributeViewImpl(final P path) {
        super(path);
    }

    @Override
    public String name() {
        return DIFF;
    }

    @Override
    public Map<String, Object> readAttributes(final String... attributes) throws IOException {
        final DiffAttributes attrs = readAttributes();

        return new HashMap<String, Object>(super.readAttributes(attributes)) {{
            for (final String attribute : attributes) {
                checkNotEmpty("attribute",
                              attribute);

                if (attribute.equals("*") || attribute.equals(DIFF)) {
                    put(DIFF,
                        attrs.branchDiff());
                }

                if (attribute.equals("*")) {
                    break;
                }
            }
        }};
    }
