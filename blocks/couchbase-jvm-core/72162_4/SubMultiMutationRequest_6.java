            if (command.attributeAccess()){
                flags |= KeyValueHandler.SUBDOC_FLAG_XATTR_PATH;
            }
            if (command.expandMacros()){
                flags |= KeyValueHandler.SUBDOC_FLAG_EXPAND_MACROS;
            }
            commandBuf.writeByte(flags);
