
package com.couchbase.client.core.message.config;

import org.junit.Test;

import static com.couchbase.client.core.message.config.GetUsersRequest.user;
import static com.couchbase.client.core.message.config.GetUsersRequest.users;
import static com.couchbase.client.core.message.config.GetUsersRequest.usersFromDomain;
import static org.junit.Assert.assertEquals;

public class GetUsersRequestTest {

    @Test
    public void shouldWorkWithNoUserAndNoDomain() {
        assertEquals("/settings/rbac/users", users("admin", "pass").path());
    }

    @Test
    public void shouldWorkWithDomainOnly() {
        assertEquals("/settings/rbac/users/domain", usersFromDomain("admin", "pass", "domain").path());
    }

    @Test
    public void shouldWorkWithDomainAndUser() {
        assertEquals("/settings/rbac/users/domain/user", user("admin", "pass", "domain", "user").path());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithUserAndNullDomain() {
        assertEquals("/settings/rbac/users/domain/user", user("admin", "pass", null, "user").path());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithUserAndEmptyDomain() {
        assertEquals("/settings/rbac/users/domain/user", user("admin", "pass", "", "user").path());
    }
}
