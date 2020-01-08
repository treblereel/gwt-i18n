//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.gwtproject.i18n.rg.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.gwtproject.i18n.ext.Resource;
import org.gwtproject.i18n.ext.ResourceOracle;
import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;

public class ResourceLocatorImpl {
    private static final int CLASS_LOADER_LOAD_REPORT_LIMIT = 10;
    private static int classLoaderLoadCount;

    public ResourceLocatorImpl() {
    }

    public static void resetClassLoaderLoadWarningCount() {
        classLoaderLoadCount = 0;
    }

    public static InputStream tryFindResourceAsStream(TreeLogger logger, ResourceOracle resourceOracle, String resourceName) {
        URL url = tryFindResourceUrl(logger, resourceOracle, resourceName);
        if (url == null) {
            return null;
        } else {
            try {
                return url.openStream();
            } catch (IOException var5) {
                return null;
            }
        }
    }

    public static URL tryFindResourceUrl(TreeLogger logger, ResourceOracle resourceOracle, String resourceName) {
        URL resource = resourceOracle.findResource(resourceName);
        if (resource != null) {
            return resource;
        }
        logger.log(TreeLogger.ERROR, "Unable to find Resource '" + resourceName + "'");
        throw new Error();
    }
}
