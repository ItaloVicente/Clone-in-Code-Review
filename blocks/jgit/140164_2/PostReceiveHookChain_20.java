            switch (i) {
                case 0:
                    return PostReceiveHook.NULL;
                case 1:
                    return newHooks[0];
                default:
                    return new PostReceiveHookChain(newHooks
            }
