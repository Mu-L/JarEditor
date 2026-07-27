package com.liubs.jareditor.decompile;

import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.compiled.ClassFileDecompilers;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;

/**
 * IDEA自带反编译器
 * @author Liubsyy
 * @date 2024/10/8
 */
public class IdeaDecompiler implements IDecompiler{

    private static ClassLoader pluginClassLoader;
    private static Object decompiler;
    private static Method decompileMethod;

    @Override
    public String decompile(Project project, VirtualFile virtualFile) {
        return decompileText(virtualFile);
    }

    private static String decompileText(VirtualFile file) {
        if(null == pluginClassLoader) {
            pluginClassLoader = getPluginClassLoader();
            if(null == pluginClassLoader) {
                return "";
            }
        }

        // org.jetbrains.java.decompiler.IdeaDecompiler ideaDecompiler = new org.jetbrains.java.decompiler.IdeaDecompiler();
        // String text =  (String) ideaDecompiler.decompile(file);
        try {
            if(null == decompiler) {
                Class<?> decompilerCls = pluginClassLoader.loadClass("org.jetbrains.java.decompiler.IdeaDecompiler");
                decompiler = decompilerCls.getConstructor().newInstance();
            }
            if(null == decompileMethod) {
                decompileMethod = decompiler.getClass().getDeclaredMethod("decompile", VirtualFile.class);
                decompileMethod.setAccessible(true);
            }
            return (String)decompileMethod.invoke(decompiler, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static ClassLoader getPluginClassLoader(){
        try{
            ClassLoader[] classLoader = new ClassLoader[1];
            BiConsumer<ClassFileDecompilers.Decompiler, PluginDescriptor> processor = (decompiler, plugin) -> {
                if ("org.jetbrains.java.decompiler".equals(plugin.getPluginId().getIdString())) {
                    classLoader[0] = plugin.getPluginClassLoader();
                }
            };
            Object extensionPointName = ClassFileDecompilers.getInstance().EP_NAME;
            // Declared on ExtensionPointName in 2020.3 and moved to its superclass in 2026.2.
            Method processMethod = extensionPointName.getClass()
                    .getMethod("processWithPluginDescriptor", BiConsumer.class);
            processMethod.invoke(extensionPointName, processor);
            return classLoader[0];
        }catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
