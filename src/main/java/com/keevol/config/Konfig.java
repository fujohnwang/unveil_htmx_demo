package com.keevol.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * {{{
 * _                                    _
 * | |                                  | |
 * | | __   ___    ___  __   __   ___   | |
 * | |/ /  / _ \  / _ \ \ \ / /  / _ \  | |
 * |   <  |  __/ |  __/  \ V /  | (_) | | |
 * |_|\_\  \___|  \___|   \_/    \___/  |_|
 * }}}
 * <p>
 * KEEp eVOLution!
 *
 * @author fq@keevol.com
 * @since 2017.5.12
 * <p>
 * Copyright 2017 © 杭州福强科技有限公司版权所有
 * [[https://www.keevol.com]]
 */
public final class Konfig {

    private Properties p = new Properties();
    
    public Konfig() throws IOException {
        // load config file as per spring boot convention as default.
        p.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
    }

    public Konfig(Properties properties) {
        this.p = properties;
    }

    public Konfig(InputStream ins) throws IOException {
        p.load(ins);
    }

    /**
     * no cast, that's users' responsibility. we give consistent expectation.
     */
    public String get(String key) {
        String env_key = key.toUpperCase().replaceAll("\\.", "_");

        String envValue = System.getenv().get(env_key);
        if (envValue != null) {
            return envValue;
        }

        String sysProp = System.getProperties().getProperty(key);
        if (!(sysProp == null || sysProp.isEmpty())) {
            return sysProp;
        }

        return p.getProperty(key);
    }

}
