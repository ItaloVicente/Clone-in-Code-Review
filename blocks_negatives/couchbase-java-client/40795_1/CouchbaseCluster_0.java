            .map(new Func1<DisconnectResponse, Boolean>() {
                     @Override
                     public Boolean call(DisconnectResponse response) {
                         return response.status() == ResponseStatus.SUCCESS;
                     }
                 }
            );
