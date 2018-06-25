package org.art.projects.java_code_wars.services.impl;

import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.services.exceptions.ServiceCompilationException;
import org.apache.log4j.Logger;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * This class provides string (acquired from the web-site) compilation
 * using special system class {@code JavaCompiler} into .class resource.
 * After string compilation .class resource is loaded by the
 * {@code URLClassLoader}. And finally a specified target method from loaded
 * class is invoked by means of <i>Reflection API</i> methods.
 */
public class StringCompilerService<T> {

    private static final Logger LOG = Logger.getLogger(StringCompilerService.class);

    private boolean diagnosticsOn;

    public StringCompilerService(boolean diagnosticsOn) {
        this.diagnosticsOn = diagnosticsOn;
    }

    /**
     * <p>{@link JavaTask} compiling with the returning of result (after method
     * invocation.
     */
    public TaskResults compileTask(JavaTask task, String initCodeString) throws ServiceCompilationException {
        String targetClassName = task.getClassName();
        String targetMethodName = task.getMethodName();
        Class[] targetMethodParametersType = task.getMethodParametersType();
        Object[] targetTestMethodParameters = task.getTestMethodParameters();
        return compile(targetClassName, targetMethodName, targetMethodParametersType,
                targetTestMethodParameters, initCodeString, diagnosticsOn, task);
    }

    private TaskResults compile(String className, String methodName, Class[] methodParametersType,
                                Object[] testMethodParameters, String source, boolean diagnosticsOn, JavaTask jTask) throws ServiceCompilationException {
        long elapsedTime = 0;
        T methodResult = null;
        Class loadedClass;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileObject file = new JavaSourceFromString(className, source);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
        boolean success = task.call();
        if (diagnosticsOn) {
            printDiagnostics(diagnostics);
        }
        if (success) {
            try {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});
                loadedClass = Class.forName(className, true, classLoader);
                Method loadedMethod = loadedClass.getDeclaredMethod(methodName, methodParametersType);
                long start = System.nanoTime();
                methodResult = (T) loadedMethod.invoke(loadedClass.newInstance(), testMethodParameters);
                long end = System.nanoTime();
                elapsedTime = (end - start) / 1000;
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | MalformedURLException | InstantiationException e) {
                LOG.info("Exception has been caught during the task compilation!");
                throw new ServiceCompilationException("Exception has been caught during the task compilation!", e);
            }
        }
        return new TaskResults(elapsedTime, methodResult);
    }

    private void printDiagnostics(DiagnosticCollector<JavaFileObject> diagnostics) {
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            LOG.trace(diagnostic.getCode());
            LOG.trace(diagnostic.getKind());
            LOG.trace(diagnostic.getPosition());
            LOG.trace(diagnostic.getStartPosition());
            LOG.trace(diagnostic.getEndPosition());
            LOG.trace(diagnostic.getSource());
            LOG.trace(diagnostic.getMessage(null));
        }
    }

    public class TaskResults {

        private long elapsedTime;
        private T methodResult;

        TaskResults(long elapsedTime, T methodResult) {
            this.elapsedTime = elapsedTime;
            this.methodResult = methodResult;
        }

        public T getMethodResult() {
            return methodResult;
        }

        public long getElapsedTime() {
            return elapsedTime;
        }

        @Override
        public String toString() {
            return "TaskResults {" +
                    "elapsed time = " + elapsedTime + " ms" +
                    ", method result = " + methodResult +
                    '}';
        }
    }
}
