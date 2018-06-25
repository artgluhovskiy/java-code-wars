package org.art.projects.java_code_wars.web.auth;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.web.filters.AuthenticationFilter;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password encoder (by means of {@link BASE64Encoder})
 */
public class Encoder {

    private static final Logger LOG = Logger.getLogger(Encoder.class);

    private static final String DEFAULT_ENCODE_ALGORITHM = "MD5";

    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(DEFAULT_ENCODE_ALGORITHM);
            md.reset();
            md.update(str.getBytes());
            byte[] dig = md.digest();
            BASE64Encoder encoder = new BASE64Encoder();
            str = encoder.encode(dig);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Incorrect encode algorithm!", e);
        }
        return str;
    }
}
