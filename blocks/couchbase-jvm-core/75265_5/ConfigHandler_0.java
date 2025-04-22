            } else if (request instanceof GetUsersRequest) {
                response = new GetUsersResponse(body, status, request);
            } else if (request instanceof UpsertUserRequest) {
                response = new UpsertUserResponse(body, status);
            }  else if (request instanceof RemoveUserRequest) {
                response = new RemoveUserResponse(status);
