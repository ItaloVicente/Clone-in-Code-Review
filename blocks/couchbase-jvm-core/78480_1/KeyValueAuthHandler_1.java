        if (ResponseStatusConverter.fromBinary(msg.getStatus()) ==
                ResponseStatus.COMMAND_UNAVAILABLE) {
            originalPromise.setSuccess();
            ctx.pipeline().remove(this);
            ctx.fireChannelActive();
            return;
        }
