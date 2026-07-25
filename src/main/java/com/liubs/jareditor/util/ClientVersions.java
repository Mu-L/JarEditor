package com.liubs.jareditor.util;

import com.intellij.ide.plugins.cl.PluginAwareClassLoader;
import com.intellij.openapi.extensions.PluginDescriptor;

/**
 * @author Liubsyy
 * @date 2025-12-9
 */
public class ClientVersions {

    /**
     * 插件版本
     * @return
     */
    public static String getCurrentPluginVersion() {
        try{
            ClassLoader classLoader = ClientVersions.class.getClassLoader();
            if (classLoader instanceof PluginAwareClassLoader) {
                PluginDescriptor plugin = ((PluginAwareClassLoader) classLoader).getPluginDescriptor();
                if (plugin != null) {
                    return plugin.getVersion();
                }
            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
}
