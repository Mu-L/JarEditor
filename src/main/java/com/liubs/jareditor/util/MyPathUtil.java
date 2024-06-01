package com.liubs.jareditor.util;

/**
 * @author Liubsyy
 * @date 2024/5/9
 */
public class MyPathUtil {

    public static final String JAR_EDIT_CLASS_PATH = "jar_edit_out";

    /**
     * /path/a.jar!/com/liubs/A.class转换成com.liubs.A
     * @param classNameInJar
     * @return
     */
    public static String getClassNameFromJar(String classNameInJar) {
        String[] split = classNameInJar.split(".jar!/");
        if(split.length!=2) {
            return null;
        }
        String replace = split[1].replace("/", ".");
        return replace.endsWith(".class") ? replace.substring(0,replace.lastIndexOf(".class")) : replace;
    }

    public static String getJarPathFromJar(String classNameInJar) {
        String[] split = classNameInJar.split(".jar!/");
        if(split.length!=2) {
            return null;
        }
        return split[0]+".jar";
    }


    public static String getEntryPathFromJar(String fullPath) {
        String[] split = fullPath.split(".jar!/");
        if(split.length!=2) {
            return null;
        }
        return split[1];
    }

    /**
     * /path/a.jar!/com/liubs/AAA.class===>AAA
     * @param classNameInJar
     * @return
     */
    public static String getJarNameFromClassJar(String classNameInJar) {
        String[] split = classNameInJar.split(".jar!/");
        if(split.length!=2) {
            return null;
        }
        int i = split[0].lastIndexOf("/");
        if(i > -1) {
            return split[0].substring(i+1);
        }
        return split[0];
    }


    /**
     * find jar path
     * /path/a.jar!/com/liubs/A.class转换成/path
     * @return
     */
    private static String getJarRootPath(String classNameInJar) {
        String[] split = classNameInJar.split(".jar!/");
        if(split.length!=2) {
            return null;
        }
        return split[0].substring(0, split[0].lastIndexOf("/"));
    }

    /**
     * 获取编译输出目录
     * /path/a.jar!/com/liubs/A.class转换成 /path/a_temp/jar_edit_out
     * @param classNameInJar
     * @return
     */
    public static String getJarEditOutput(String classNameInJar){
        String[] split = classNameInJar.split(".jar!/");
        if(split.length!=2) {
            return null;
        }

        return split[0]+"_temp/"+JAR_EDIT_CLASS_PATH;
    }
    public static String getJarEditTemp(String classNameInJar){
        String[] split = classNameInJar.split(".jar!/");
        if(split.length!=2) {
            return null;
        }

        return split[0]+"_temp";
    }


}
