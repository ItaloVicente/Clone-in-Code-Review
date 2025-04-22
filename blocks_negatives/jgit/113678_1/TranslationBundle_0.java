 * according to the locale set with {@link NLS#setLocale(Locale)}. However, the
 * {@link NLS#setLocale(Locale)} method defines only prefered locale which will
 * be honored only if it is supported by the provided resource bundle property
 * files. Basically, this class will use
 * {@link ResourceBundle#getBundle(String, Locale)} method to load a resource
 * bundle. See the documentation of this method for a detailed explanation of
 * resource bundle loading strategy. After a bundle is created the
 * {@link #effectiveLocale()} method can be used to determine whether the bundle
 * really corresponds to the requested locale or is a fallback.
