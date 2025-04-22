 * This interface is used as API for the Readme parser extension
 * point. The default implementation simply looks for lines
 * in the file that start with a number and assumes that they
 * represent sections. Tools are allowed to replace this
 * algorithm by defining an extension and supplying an
 * alternative that implements this interface.
