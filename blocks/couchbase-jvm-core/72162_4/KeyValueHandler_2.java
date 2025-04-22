            if (mut.attributeAccess()) {
                flags |= SUBDOC_FLAG_XATTR_PATH;
            }
            if (mut.expandMacros()) {
                flags |= SUBDOC_FLAG_EXPAND_MACROS;
            }
            extras.writeByte(flags);

