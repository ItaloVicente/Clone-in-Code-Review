 * <li> A typical UI plug-in will have some images that are used very frequently
 *      and so need to be cached and shared.  The plug-in's image registry
 *      provides a central place for a plug-in to store its common images.
 *      Images managed by the registry are created lazily as needed, and will be
 *      automatically disposed of when the plug-in shuts down. Note that the
 *      number of registry images should be kept to a minimum since many OSs
 *      have severe limits on the number of images that can be in memory at once.
