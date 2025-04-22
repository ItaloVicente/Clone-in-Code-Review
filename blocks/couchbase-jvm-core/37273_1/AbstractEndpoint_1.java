                });*/
                    channel.write(request, channel.voidPromise());
                    hasWritten = true;
                } else {
                    responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable());
                }
