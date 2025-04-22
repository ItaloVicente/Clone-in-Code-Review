     *  Initialize the image registry by declaring all of the required
     *  graphics. This involves creating JFace image descriptors describing
     *  how to create/find the image should it be needed.
     *  The image is not actually allocated until requested.
     *
     *  Prefix conventions
     *      Wizard Banners          WIZBAN_
     *      Preference Banners      PREF_BAN_
     *      Property Page Banners   PROPBAN_
     *      Enable toolbar          ETOOL_
     *      Disable toolbar         DTOOL_
     *      Local enabled toolbar   ELCL_
     *      Local Disable toolbar   DLCL_
     *      Object large            OBJL_
     *      Object small            OBJS_
     *      View                    VIEW_
     *      Product images          PROD_
     *      Misc images             MISC_
     *
     *  Where are the images?
     *      The images (typically gifs) are found in the same location as this plugin class.
     *      This may mean the same package directory as the package holding this class.
     *      The images are declared using this.getClass() to ensure they are looked up via
     *      this plugin class.
     *  @see ImageRegistry
     */
