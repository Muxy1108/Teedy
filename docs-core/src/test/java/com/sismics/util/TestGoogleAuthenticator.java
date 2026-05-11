package com.sismics.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.sismics.util.totp.GoogleAuthenticator;
import com.sismics.util.totp.GoogleAuthenticatorKey;

/**
 * Test of {@link GoogleAuthenticator}
 * 
 * @author bgamard
 */
public class TestGoogleAuthenticator {
    @Test
    public void testGoogleAuthenticator() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        Assert.assertNotNull(key.getVerificationCode());
        Assert.assertEquals(5, key.getScratchCodes().size());
        int validationCode = gAuth.calculateCode(key.getKey(), new Date().getTime() / 30000);
        Assert.assertTrue(gAuth.authorize(key.getKey(), validationCode));
    }

    @Test
    public void testAuthorizeWithInvalidCode() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        long timeWindow = new Date().getTime() / 30000;
        int validationCode = gAuth.calculateCode(key.getKey(), timeWindow);
        int invalidCode = (validationCode + 1) % 1000000;

        Assert.assertFalse(gAuth.authorize(key.getKey(), invalidCode));
    }

    @Test
    public void testCalculateCodeIsStableForSameTimeWindow() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        long timeWindow = new Date().getTime() / 30000;
        int firstCode = gAuth.calculateCode(key.getKey(), timeWindow);
        int secondCode = gAuth.calculateCode(key.getKey(), timeWindow);

        Assert.assertEquals(firstCode, secondCode);
    }

    @Test
    public void testAuthorizeWithOldCode() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        long currentTimeWindow = new Date().getTime() / 30000;
        int oldCode = gAuth.calculateCode(key.getKey(), currentTimeWindow - 1000);

        Assert.assertFalse(gAuth.authorize(key.getKey(), oldCode));
    }
}