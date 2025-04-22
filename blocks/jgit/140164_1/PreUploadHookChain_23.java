            switch (i) {
                case 0:
                    return PreUploadHook.NULL;
                case 1:
                    return newHooks[0];
                default:
                    return new PreUploadHookChain(newHooks
            }
