            switch (i) {
                case 0:
                    return PostUploadHook.NULL;
                case 1:
                    return newHooks[0];
                default:
                    return new PostUploadHookChain(newHooks
            }
