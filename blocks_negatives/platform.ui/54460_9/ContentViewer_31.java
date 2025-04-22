 * A viewer's model consists of elements, represented by objects.
 * A viewer defines and implements generic infrastructure for handling model
 * input, updates, and selections in terms of elements.
 * Input is obtained by querying an <code>IContentProvider</code> which returns
 * elements. The elements themselves are not displayed directly.  They are
 * mapped to labels, containing text and/or an image, using the viewer's
 * <code>ILabelProvider</code>.
